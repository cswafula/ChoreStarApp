package com.example.charlie.chorestarapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class LoginPage extends AppCompatActivity {
    public EditText Email, Password;
    private Button login, signup;
    ProgressBar progressBar;
    private String email, password;
    HttpURLConnection connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        login=(Button) findViewById(R.id.login_button);
        signup= (Button) findViewById(R.id.signup_button);
        progressBar= findViewById(R.id.LoginProgress);
        Email= (EditText) findViewById(R.id.LoginEmail);
        Password= (EditText) findViewById(R.id.LoginPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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

    private void login() {
        email=Email.getText().toString().trim();
        password=Password.getText().toString().trim();
        try{
            URL url= new URL("127.0.0.1:8000/api/insert/"+email+"/"+password);
            connection=(HttpURLConnection) url.openConnection();
            connection.connect();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
