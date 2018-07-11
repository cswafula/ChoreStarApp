package com.example.charlie.chorestarapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
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

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {

    private Context mCtx;
    private List<Profile> profileList;

    public ProfileAdapter(Context mCtx, List<Profile> profileList) {
        this.mCtx = mCtx;
        this.profileList = profileList;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.profile_recycler_list,null);
        return new ProfileAdapter.ProfileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {
        final Profile profile=profileList.get(position);
        holder.profileImage.setImageDrawable(mCtx.getResources().getDrawable(profile.getProfileImage()));
        holder.Cname.setText(profile.getProfileName());
        holder.Cage.setText(profile.getProfileAge());
        holder.Cpoints.setText(profile.getProfilePoints());
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.init(mCtx);
                String Email=Paper.book().read("LoginEmail").toString();
                final String DELETE_URL="https://chorestar95049.herokuapp.com/api/DeleteProfile/"+Email+"/"+profile.getProfileName();

                AlertDialog.Builder builder= new AlertDialog.Builder(mCtx);
                builder.setTitle("Delete "+profile.getProfileName());
                builder.setMessage("This profile will be deleted permanently");
                builder.setCancelable(true);
                builder.setNegativeButton("DELETE!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        StringRequest stringRequest=new StringRequest(Request.Method.GET, DELETE_URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(mCtx,"Deleted",Toast.LENGTH_LONG).show();
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
        return profileList.size();
    }

    class ProfileViewHolder extends RecyclerView.ViewHolder{

        ImageView profileImage,Delete;
        TextView Cname,Cage,Cpoints;

        public ProfileViewHolder(View itemView) {
            super(itemView);

            profileImage=itemView.findViewById(R.id.ProfileImage);
            Cname=itemView.findViewById(R.id.ProfileName);
            Cage=itemView.findViewById(R.id.ProfileAge);
            Cpoints=itemView.findViewById(R.id.ProfilePoints);
            Delete=itemView.findViewById(R.id.ProfileDelete);

        }
    }
}
