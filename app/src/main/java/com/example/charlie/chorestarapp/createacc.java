package com.example.charlie.chorestarapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class createacc extends AppCompatActivity {
    private Button cancel,btncontinue;
    private EditText Email, NickName, Pass,confirm;
    private ProgressBar progressBar;

//    public static final String REGISTER_URL="http://www.racecoursehospital.com/charlie/register.php";
//    public static final String KEY_EMAIL="emailaddress";
//    public static final String KEY_PASS="password";

    public static final String REGISTER_URL="http://192.168.0.33:8000/api/api_register";
    public static final String KEY_EMAIL="emailaddress";
    public static final String KEY_NICKNAME="nickname";
    public static final String KEY_PASS="password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createacc);
        Email= (EditText) findViewById(R.id.Register_Email);
        NickName= (EditText) findViewById(R.id.Register_Nickname);
        Pass= (EditText) findViewById(R.id.Register_Pass);
        confirm= (EditText) findViewById(R.id.Register_Pass_confirm);
        cancel = (Button) findViewById(R.id.createacc_button_cancel);
        btncontinue= (Button) findViewById(R.id.createacc_button_continue);
        progressBar=(ProgressBar) findViewById(R.id.progress_createacc);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {

    final String email,password,nickename;
    email=Email.getText().toString().trim();
    password=Pass.getText().toString().trim();
    nickename=NickName.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put(KEY_EMAIL,email);
                params.put(KEY_NICKNAME,nickename);
                params.put(KEY_PASS,password);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder= new AlertDialog.Builder(createacc.this);
        builder.setMessage("Are you sure you want to go back, you're almost done");
        builder.setCancelable(true);
        builder.setNegativeButton("No, Stay!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes, Close!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(createacc.this, LoginPage.class));
                finish();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}


//    private void registerUser() {
//
//        final String email,password;
//        email=Email.getText().toString().trim();
//        password=Pass.getText().toString().trim();
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
//                    }
//                }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params=new HashMap<>();
//                params.put(KEY_EMAIL,email);
//                params.put(KEY_PASS,password);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue= Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//
//    }

