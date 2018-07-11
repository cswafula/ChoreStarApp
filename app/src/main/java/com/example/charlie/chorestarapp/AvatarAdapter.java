package com.example.charlie.chorestarapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class AvatarAdapter extends ArrayAdapter<Avatar> {

    public AvatarAdapter(Context context, ArrayList<Avatar> avatarList) {
        super(context, 0,avatarList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position,View convertView,ViewGroup parent){
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.avatar_selector,parent,false);
        }
        ImageView avatarImage=convertView.findViewById(R.id.AvatarImage);

        Avatar avatarItem= getItem(position);

        if(avatarItem!=null){
            avatarImage.setImageResource(avatarItem.getAvatarImage());
        }
        return convertView;
    }
}
