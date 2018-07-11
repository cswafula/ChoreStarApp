package com.example.charlie.chorestarapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class LoginPage extends AppCompatActivity {
    public static String LOGIN_URL;
    public static final String KEY_EMAIL="Email";
    public static final String KEY_PASS="Password";


    public EditText Email, Password;
    private Button login, signup;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login=(Button) findViewById(R.id.login_button);
        signup= (Button) findViewById(R.id.signup_button);
        progressBar= findViewById(R.id.LoginProgress);
        Email= (EditText) findViewById(R.id.LoginEmail);
        Password= (EditText) findViewById(R.id.LoginPassword);

        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,createacc.class));
                finish();
            }
        });

    }

    private void validate() {
        String email,password;
        email=Email.getText().toString().trim();
        password=Password.getText().toString().trim();
        if(email.isEmpty()){
            Email.setError("Email is required!");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email.setError("Email is Invalid");
        }else if(password.isEmpty()){
            Password.setError("Password is required!");
        }else{
            LOGIN_URL="https://chorestar95049.herokuapp.com/api/Login/"+email+"/"+password;
            login();
        }
    }

    private void login() {


        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loging In...");
        progressDialog.show();

        final String Login_email, Login_password;
        Login_email=Email.getText().toString().trim();
        Login_password=Password.getText().toString().trim();

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, LOGIN_URL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("User");
                    String Email=jsonArray.get(0).toString();
                    String Password=jsonArray.get(1).toString();
                    String Nickname=jsonArray.get(2).toString();
                    String Status="true";
                    Paper.book().write("LoginEmail",Email);
                    Paper.book().write("LoginPassword",Password);
                    Paper.book().write("LoginNickName",Nickname);
                    Paper.book().write("LoginStatus",Status);
                    Paper.book().write("Splash","Parent");
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Login Successful!",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginPage.this,ParentHomepage.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}