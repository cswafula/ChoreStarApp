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

public class ChoresFragment extends Fragment {

    FloatingActionButton fab;

    RecyclerView recyclerView;
    ChoreListAdapter adapter;
    List<ChoreList> choreLists;

    private static String FETCH_URL;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chores,null);

        fab=view.findViewById(R.id.ChoresFAB);
        recyclerView=view.findViewById(R.id.ChoresRecycler);

        Paper.init(view.getContext());
        String Email=Paper.book().read("LoginEmail").toString();

        FETCH_URL="https://chorestar95049.herokuapp.com/api/FetchChores/"+Email;

        choreLists=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),AddChore.class));
            }
        });

        final ProgressDialog progressDialog=new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading Chores List...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.GET, FETCH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            JSONArray jsonArray=jsonObject.getJSONArray("Chores");

                            for(int i=0 ; i<jsonArray.length();i++){
                                JSONObject object=jsonArray.getJSONObject(i);
                                choreLists.add(new ChoreList(Integer.valueOf(object.getString("ChoreImage")),object.getString("ChoreName")
                                        ,"Value Points: "+object.getString("ChorePoints"),"For: "+object.getString("ChildName")));
                            }
                            adapter= new ChoreListAdapter(getContext(),choreLists);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);

        return view;
    }
}
