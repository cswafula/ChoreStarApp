package com.example.charlie.chorestarapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
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

import io.paperdb.Paper;

public class createacc extends AppCompatActivity {

    public static final String REGISTER_URL="https://chorestar95049.herokuapp.com/api/Register";
    public static final String KEY_EMAIL="Email";
    public static final String KEY_NICKNAME="Nickname";
    public static final String KEY_PASS="Password";


    private Button cancel,btncontinue;
    private EditText Email, NickName, Pass,confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createacc);
        Email= (EditText) findViewById(R.id.Register_Email);
        NickName= (EditText) findViewById(R.id.Register_Nickname);
        Pass= (EditText) findViewById(R.id.Register_Pass);
        confirm= (EditText) findViewById(R.id.Register_Pass_confirm);
        btncontinue= (Button) findViewById(R.id.createacc_button_continue);

        Paper.init(this);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        String EmailAddress=Email.getText().toString().trim();
        String Password= Pass.getText().toString().trim();
        String nick_name= NickName.getText().toString().trim();
        String ConfirmPass= confirm.getText().toString().trim();

        if(EmailAddress.isEmpty()){
            Email.setError("Email address is Required");
        }else if(nick_name.isEmpty()){
            NickName.setError("This Field is Required");
        }
        else if(Password.isEmpty()){
            Pass.setError("Please Enter Password");
        }else if(ConfirmPass.isEmpty()){
            confirm.setError("Please confirm your password");
        }else if(!Password.equals( ConfirmPass)){
            confirm.setError("Passwords don't match");
        }else if(!Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()){
            Email.setError("Please enter a valid Email address");
        }else if(Password.length() < 6){
            Pass.setError("Password shouldn't be less than 6 words");
        }else{
            Paper.book().write("Email",EmailAddress);
            Paper.book().write("Password",Password);
            Paper.book().write("Nickname",nick_name);
            CreateParentAccount();
        }
    }

    private void CreateParentAccount() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Creating Parent's Account....");
        progressDialog.show();

        final String email,password,nickename;
        email= Paper.book().read("Email");
        password=Paper.book().read("Password");
        nickename=Paper.book().read("Nickname");

        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(new Intent(createacc.this, CreateChildAcc.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put(KEY_EMAIL,email);
                params.put(KEY_PASS,password);
                params.put(KEY_NICKNAME,nickename);
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