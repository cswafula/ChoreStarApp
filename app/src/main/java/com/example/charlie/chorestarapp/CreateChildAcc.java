package com.example.charlie.chorestarapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class CreateChildAcc extends AppCompatActivity {

    public static final String REGISTER_URL="https://chorestar95049.herokuapp.com/api/RegisterChild";
    public static final String KEY_EMAIL="Email";
    public static final String KEY_CHILDNAME="ChildName";
    public static final String KEY_CHILDIMAGE="ChildImage";
    public static final String KEY_AGE="Age";
    public static final String KEY_POINTS="Points";

    public Button Create;
    private TextView childname,childage,UserNickName;
    private Spinner spinner;
    private ArrayList<Avatar> avatarList;
    private AvatarAdapter avatarAdapter;
    String Child_Image="";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child_acc);

        Create= (Button) findViewById(R.id.childaccount_finish);
        childname= (TextView) findViewById(R.id.childname_createacc);
        UserNickName= (TextView) findViewById(R.id.txt_NickName);
        String nick_name2=Paper.book().read("Nickname");
        UserNickName.setText("Hello "+ nick_name2);
        spinner=findViewById(R.id.AvatarSpinner);
        childage=findViewById(R.id.childAge_createacc);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });

        Paper.init(this);
        initList();

        avatarAdapter=new AvatarAdapter(this,avatarList);
        spinner.setAdapter(avatarAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Avatar ClickedAvatar=(Avatar) parent.getItemAtPosition(position);
                int ClickedImage=ClickedAvatar.getAvatarImage();
                String fetch=String.valueOf(ClickedImage);
                Child_Image=fetch;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initList() {
        avatarList=new ArrayList<>();
        avatarList.add(new Avatar(R.drawable.avatar1));
        avatarList.add(new Avatar(R.drawable.avatar2));
        avatarList.add(new Avatar(R.drawable.avatar3));
        avatarList.add(new Avatar(R.drawable.avatar4));
        avatarList.add(new Avatar(R.drawable.avatar5));
    }

    private void Validate() {
        String Childname= childname.getText().toString().trim();
        String ChildAge=childage.getText().toString().trim();
        if(Childname.isEmpty()){
            childname.setError("Enter your child's name");
        }else if(Child_Image.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select your child's avatar",Toast.LENGTH_LONG).show();
        }else if(ChildAge.isEmpty()){
            childage.setError("Enter Child age");
        }else{
            int age=Integer.valueOf(ChildAge);
            if(age<=0 || age>=15){
                childage.setError("Age is not Valid");
            }else {
                Paper.book().write("ChildName",Childname);
                Paper.book().write("ChildAge",ChildAge);
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
        email= Paper.book().read("Email");
        child_name=Paper.book().read("ChildName");
        child_image=Child_Image;
        child_age=Paper.book().read("ChildAge");

        StringRequest stringRequest= new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                        String Status="true";
                        Paper.book().write("LoginEmail",email);
                        Paper.book().write("LoginPassword",Paper.book().read("Password"));
                        Paper.book().write("LoginNickName",Paper.book().read("Nickname"));
                        Paper.book().write("LoginChildName",child_name);
                        Paper.book().write("LoginStatus",Status);
                        Paper.book().write("Splash","Child");
                        finish();
                        startActivity(new Intent(CreateChildAcc.this, ParentHomepage.class));
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

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder= new AlertDialog.Builder(CreateChildAcc.this);
        builder.setMessage("Sorry you must create your child's profile first");
        builder.setCancelable(true);
        builder.setNegativeButton("Stay and Finish!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
}
