package com.example.charlie.chorestarapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import io.paperdb.Paper;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertsViewHolder> {
    private Context mCtx;
    private List<Alerts> alertsList;

    public AlertsAdapter(Context mCtx, List<Alerts> alertsList) {
        this.mCtx = mCtx;
        this.alertsList = alertsList;
    }

    @NonNull
    @Override
    public AlertsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.child_chore_list,null);
        return new AlertsAdapter.AlertsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertsViewHolder holder, int position) {
        final Alerts alerts=alertsList.get(position);
        holder.ChoreImage.setImageDrawable(mCtx.getResources().getDrawable(alerts.getChoreImage()));
        holder.ChildName.setText(alerts.getChoreName());
        holder.ChorePoints.setText(alerts.getChildName());
        holder.ChoreName.setText(alerts.getChorePoints());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(mCtx);
                builder.setMessage("Whats your review for the work done?");
                builder.setCancelable(true);
                builder.setNegativeButton("DECLINE!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Paper.init(mCtx);
                        String Email=Paper.book().read("LoginEmail").toString();
                        Toast.makeText(mCtx,alerts.getChildName()+"/"+alerts.getChoreName(),Toast.LENGTH_LONG).show();

                        String DECLINE_URL="https://chorestar95049.herokuapp.com/api/ChoreDecline/"+Email+"/"+alerts.getChildName()+"/"+alerts.getChoreName();

                        StringRequest stringRequest=new StringRequest(Request.Method.GET, DECLINE_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(mCtx,"Declined",Toast.LENGTH_LONG).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        RequestQueue requestQueue= Volley.newRequestQueue(mCtx);
                        requestQueue.add(stringRequest);

                    }
                });
                builder.setPositiveButton("ACCEPT!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Paper.init(mCtx);
                        String Email=Paper.book().read("LoginEmail").toString();
                        Toast.makeText(mCtx,alerts.getChildName()+"/"+alerts.getChoreName()+"/"+alerts.getChorePoints(),Toast.LENGTH_LONG).show();
                        String ACCEPT_URL="https://chorestar95049.herokuapp.com/api/ChoreAccept/"+Email+"/"+alerts.getChildName()+"/"+alerts.getChoreName()+"/"+alerts.getChorePoints();

                        StringRequest stringRequest=new StringRequest(Request.Method.GET, ACCEPT_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(mCtx,"Accepted",Toast.LENGTH_LONG).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        RequestQueue requestQueue= Volley.newRequestQueue(mCtx);
                        requestQueue.add(stringRequest);


                    }
                });
                AlertDialog alertDialog= builder.create();
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return alertsList.size();
    }

    class AlertsViewHolder extends RecyclerView.ViewHolder{
        ImageView ChoreImage;
        TextView ChoreName,ChorePoints,ChildName;
        ConstraintLayout constraintLayout;

        public AlertsViewHolder(View itemView) {
            super(itemView);

            constraintLayout=itemView.findViewById(R.id.ChoresDetailsConstraint);
            ChoreName=itemView.findViewById(R.id.ChoreName);
            ChoreImage=itemView.findViewById(R.id.ChoreImage);
            ChorePoints=itemView.findViewById(R.id.ChorePoints);
            ChildName=itemView.findViewById(R.id.ChoreChildName);
        }
    }
}
