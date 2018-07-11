package com.example.charlie.chorestarapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ChildHomepage extends AppCompatActivity {
    RecyclerView recyclerView;
    ChoreAdapter adapter;
    List<Chore> choreList;

    private static String FETCH_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_homepage);
        recyclerView=findViewById(R.id.ChildChoresRecycler);

        Paper.init(this);
        String Email=Paper.book().read("LoginEmail").toString();

        FETCH_URL="https://chorestar95049.herokuapp.com/api/FetchChores/"+Email;

        choreList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ProgressDialog progressDialog=new ProgressDialog(this);
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
                                choreList.add(new Chore(Integer.valueOf(object.getString("ChoreImage")),object.getString("ChoreName")
                                        ,"For: "+object.getString("ChildName"),object.getString("ChorePoints")));
                            }
                            adapter= new ChoreAdapter(ChildHomepage.this,choreList);
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
