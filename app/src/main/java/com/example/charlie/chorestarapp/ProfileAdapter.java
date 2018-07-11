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

import java.util.List;

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
                AlertDialog.Builder builder= new AlertDialog.Builder(mCtx);
                builder.setTitle("Delete "+profile.getProfileName());
                builder.setMessage("This profile will be deleted permanently");
                builder.setCancelable(true);
                builder.setNegativeButton("DELETE!", new DialogInterface.OnClickListener() {
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
