package com.example.charlie.chorestarapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    String Email,Password,Nickname,Status;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        prefs = getSharedPreferences("com.example.charlie.chorestarapp", MODE_PRIVATE);
        Paper.init(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String empty="";
                    Paper.book().write("LoginEmail",empty);
                    Paper.book().write("LoginPassword",empty);
                    Paper.book().write("LoginNickName",empty);
                    Paper.book().write("LoginStatus",empty);
                    startActivity(new Intent(SplashScreen.this,LoginPage.class));
                    finish();
                }
            },SPLASH_TIME_OUT);
            prefs.edit().putBoolean("firstrun", false).apply();
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkSession();
                }
            },SPLASH_TIME_OUT);
        }

    }

    private void checkSession() {
        Email=Paper.book().read("LoginEmail").toString();
        Password=Paper.book().read("LoginPassword").toString();
        Nickname=Paper.book().read("LoginNickName").toString();
        Status=Paper.book().read("LoginStatus").toString();
        if(Email.isEmpty() || Password.isEmpty() || Nickname.isEmpty() || Status.isEmpty()){
                startActivity(new Intent(SplashScreen.this,LoginPage.class));
                finish();
        }else{
            startActivity(new Intent(SplashScreen.this,ParentHomepage.class));
            finish();
        }
    }

}
