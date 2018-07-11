package com.example.charlie.chorestarapp;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import io.paperdb.Paper;

public class ChoreDetails extends AppCompatActivity {
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
        String ChildName=Paper.book().read("Childname").toString();
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

    }
}
