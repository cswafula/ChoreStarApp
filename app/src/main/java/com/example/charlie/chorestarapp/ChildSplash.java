package com.example.charlie.chorestarapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.paperdb.Paper;

public class ChildSplash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_splash);
        mediaPlayer=MediaPlayer.create(this,R.raw.yaay);
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(ChildSplash.this,ChildHomepage.class));
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
