package com.example.charlie.chorestarapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.roger.catloadinglibrary.CatLoadingView;

import io.paperdb.Paper;

public class ChoreDetails extends AppCompatActivity {
    public static String UPDATE_URL;
    ImageView Accept,Decline;
    TextView Chore_name,Chore_points;
    ImageView imageView;
    MediaPlayer mpBoo,mpYaay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chore_details);

        Accept=findViewById(R.id.AcceptChore);
        Decline=findViewById(R.id.DeclineChore);
        Chore_name=findViewById(R.id.DetailsChoreName);
        Chore_points=findViewById(R.id.DetailsChorePoints);
        imageView=findViewById(R.id.DetailsChoreImage);
        mpBoo=MediaPlayer.create(this,R.raw.boo);
        mpYaay=MediaPlayer.create(this,R.raw.yaay);

        Paper.init(this);
        String ChoreName= Paper.book().read("Chorename").toString();
        String ChorePoints=Paper.book().read("Chorepoints").toString();
        int Image=Integer.valueOf(Paper.book().read("Image").toString());

        Chore_name.setText(ChoreName);
        Chore_points.setText(ChorePoints);
        imageView.setImageDrawable(this.getResources().getDrawable(Image));

        Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpBoo.start();
                finish();
            }
        });

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mpYaay.start();
                Alert();
            }
        });

    }

    private void Alert() {
        CatLoadingView mView;
        mView=new CatLoadingView();
        mView.show(getSupportFragmentManager(),"Loading");

        String Email=Paper.book().read("LoginEmail");
        String ChildName=Paper.book().read("Childname").toString();
        String Chore_Name= Paper.book().read("Chorename").toString();


        UPDATE_URL="https://chorestar95049.herokuapp.com/api/ChoreDone/"+Email+"/"+ChildName+"/"+Chore_Name;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, UPDATE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finish();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
