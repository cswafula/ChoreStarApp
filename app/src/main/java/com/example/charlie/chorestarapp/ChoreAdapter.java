package com.example.charlie.chorestarapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.paperdb.Paper;

public class ChoreAdapter extends RecyclerView.Adapter<ChoreAdapter.ChoreViewHolder> {
    private Context mCtx;
    private List<Chore> choreList;

    public ChoreAdapter(Context mCtx, List<Chore> choreList) {
        this.mCtx = mCtx;
        this.choreList = choreList;
    }

    @NonNull
    @Override
    public ChoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view=inflater.inflate(R.layout.child_chore_list,null);
        return new ChoreAdapter.ChoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChoreViewHolder holder, int position) {
        final Chore chore=choreList.get(position);
        holder.ChoreImage.setImageDrawable(mCtx.getResources().getDrawable(chore.getChoreImage()));
        holder.ChildName.setText(chore.getChoreChildName());
        holder.ChorePoints.setText(chore.getChorePoints());
        holder.ChoreName.setText(chore.getChoreName());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.init(mCtx);
                Paper.book().write("Image",chore.getChoreImage());
                Paper.book().write("Childname",chore.getChoreChildName());
                Paper.book().write("Chorename",chore.getChoreName());
                Paper.book().write("Chorepoints",chore.getChorePoints());
                Intent intent=new Intent(mCtx,ChoreDetails.class);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return choreList.size();
    }

    class ChoreViewHolder extends RecyclerView.ViewHolder{

        ImageView ChoreImage;
        TextView ChoreName,ChorePoints,ChildName;
        ConstraintLayout constraintLayout;

        public ChoreViewHolder(View itemView) {
            super(itemView);

            constraintLayout=itemView.findViewById(R.id.ChoresDetailsConstraint);
            ChoreName=itemView.findViewById(R.id.ChoreName);
            ChoreImage=itemView.findViewById(R.id.ChoreImage);
            ChorePoints=itemView.findViewById(R.id.ChorePoints);
            ChildName=itemView.findViewById(R.id.ChoreChildName);

        }
    }
}
