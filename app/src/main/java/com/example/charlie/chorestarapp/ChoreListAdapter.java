package com.example.charlie.chorestarapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ChoreListAdapter extends RecyclerView.Adapter<ChoreListAdapter.ChoreListViewHolder> {
    private Context mCtx;
    private List<ChoreList> choreLists;

    public ChoreListAdapter(Context mCtx, List<ChoreList> choreLists) {
        this.mCtx = mCtx;
        this.choreLists = choreLists;
    }

    @NonNull
    @Override
    public ChoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.chore_list_fetch,null);
        return new ChoreListAdapter.ChoreListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoreListViewHolder holder, int position) {
        final ChoreList choreList=choreLists.get(position);
        holder.profileImage.setImageDrawable(mCtx.getResources().getDrawable(choreList.getProfileImage()));
        holder.Cname.setText(choreList.getProfileName());
        holder.Cage.setText(choreList.getProfileAge());
        holder.Cpoints.setText(choreList.getProfilePoints());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(mCtx);
                builder.setTitle(choreList.getProfileName());
                builder.setIcon(mCtx.getResources().getDrawable(choreList.getProfileImage()));
                builder.setMessage(choreList.getProfileAge());
                builder.setCancelable(true);
                builder.setNegativeButton("DELETE CHORE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                });
                AlertDialog alertDialog= builder.create();
                alertDialog.show();
            }
        });


    }

    private void delete() {
        String DELETE_URL="https://chorestar95049.herokuapp.com/api/DeleteChore/";
    }

    @Override
    public int getItemCount() {
        return choreLists.size();
    }


    class ChoreListViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        ImageView profileImage;
        TextView Cname,Cage,Cpoints;

        public ChoreListViewHolder(View itemView) {
            super(itemView);
            profileImage=itemView.findViewById(R.id.ChoreListProfileImage);
            Cname=itemView.findViewById(R.id.ChoreListProfileName);
            Cage=itemView.findViewById(R.id.ChoreListProfileAge);
            Cpoints=itemView.findViewById(R.id.ChoreListProfilePoints);
            linearLayout=itemView.findViewById(R.id.ChoreListLinearLayout);
        }
    }
}


