package com.example.charlie.chorestarapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class ProfileFragment extends Fragment {

    RecyclerView recyclerView;
    ProfileAdapter adapter;
    List<Profile> profileList;

    private static String FETCH_URL;

    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View mView= inflater.inflate(R.layout.fragment_profile,null);

        Paper.init(mView.getContext());
        String Email=Paper.book().read("LoginEmail").toString();

        FETCH_URL="https://chorestar95049.herokuapp.com/api/FetchProfile/"+Email;

        profileList=new ArrayList<>();
        recyclerView=mView.findViewById(R.id.Profile_Recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
        fab=mView.findViewById(R.id.FragmentProfileFAB);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mView.getContext(),AddChildProfile.class));
            }
        });

        final ProgressDialog progressDialog=new ProgressDialog(mView.getContext());
        progressDialog.setMessage("Loading Children Profiles....");
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.GET, FETCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("Profiles");

                            for(int i=0 ; i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                profileList.add(new Profile(Integer.valueOf(object.getString("ChildImage")),object.getString("ChildName")
                                        ,"Points: "+object.getString("Points"),"Age: "+object.getString("Age")));
                            }
                            adapter= new ProfileAdapter(mView.getContext(),profileList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(mView.getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(mView.getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(mView.getContext());
        requestQueue.add(stringRequest);

        return mView;
    }
}