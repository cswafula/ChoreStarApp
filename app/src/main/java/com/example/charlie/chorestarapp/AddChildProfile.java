package com.example.charlie.chorestarapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class AddChildProfile extends AppCompatActivity {

    public static final String REGISTER_URL="https://chorestar95049.herokuapp.com/api/RegisterChild";
    public static final String KEY_EMAIL="Email";
    public static final String KEY_CHILDNAME="ChildName";
    public static final String KEY_CHILDIMAGE="ChildImage";
    public static final String KEY_AGE="Age";
    public static final String KEY_POINTS="Points";

    private Spinner spinner;
    private ArrayList<Avatar> avatarList;
    private AvatarAdapter avatarAdapter;
    String Child_Image = "";
    EditText Name, Age;
    Button save;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_profile);

        spinner = findViewById(R.id.NewChildAvatar);
        Name = findViewById(R.id.newProfile_name);
        Age = findViewById(R.id.newProfile_Age);
        save = findViewById(R.id.newProfile_Save);
        Paper.init(this);
        Email=Paper.book().read("LoginEmail");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });


        initList();
        avatarAdapter = new AvatarAdapter(this, avatarList);
        spinner.setAdapter(avatarAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Avatar ClickedAvatar = (Avatar) parent.getItemAtPosition(position);
                int ClickedImage = ClickedAvatar.getAvatarImage();
                Child_Image = String.valueOf(ClickedImage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initList() {
        avatarList = new ArrayList<>();
        avatarList.add(new Avatar(R.drawable.avatar1));
        avatarList.add(new Avatar(R.drawable.avatar2));
        avatarList.add(new Avatar(R.drawable.avatar3));
        avatarList.add(new Avatar(R.drawable.avatar4));
        avatarList.add(new Avatar(R.drawable.avatar5));
    }

    private void Validate() {
        String Childname = Name.getText().toString().trim();
        String ChildAge = Age.getText().toString().trim();
        if (Childname.isEmpty()) {
            Name.setError("Enter your child's name");
        } else if (Child_Image.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select your child's avatar", Toast.LENGTH_LONG).show();
        } else if (ChildAge.isEmpty()) {
            Age.setError("Enter Child age");
        } else {
            int age = Integer.valueOf(ChildAge);
            if (age <= 0 || age >= 15) {
                Age.setError("Age is not Valid");
            } else {
                registerUser();
            }

        }
    }

    private void registerUser() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Creating Child's Profile....");
        progressDialog.show();

        final String email,child_name,child_image,child_age,child_points;
        int points=0;
        child_points=String.valueOf(points);
        email=Email;
        child_name=Name.getText().toString();
        child_image=Child_Image;
        child_age=Age.getText().toString();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        finish();
                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
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
                params.put(KEY_CHILDNAME,child_name);
                params.put(KEY_CHILDIMAGE,child_image);
                params.put(KEY_AGE,child_age);
                params.put(KEY_POINTS,child_points);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}