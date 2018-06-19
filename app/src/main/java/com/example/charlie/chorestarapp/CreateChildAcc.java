package com.example.charlie.chorestarapp;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class CreateChildAcc extends AppCompatActivity {
    public static final String REGISTER_URL="https://chorestar95049.herokuapp.com/api/Register";
    public static final String KEY_EMAIL="Email";
    public static final String KEY_NICKNAME="Nickname";
    public static final String KEY_PASS="Password";
    public static final String KEY_CHILDNAME="ChildName";
    public static final String KEY_CHILDIMAGE="ChildImage";


    public Button Create,Upload;
    private static final int PICK_IMAGE_REQUEST=1;
    private ImageView imageGet;
    private TextView childname;
    private Uri mImageUri;
    private TextView UserEmail, UserNickName;
    ProgressBar mProgressBar;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_child_acc);

        mProgressBar=findViewById(R.id.createchildaccProgress);
        Create= (Button) findViewById(R.id.childaccount_finish);
        Upload= (Button) findViewById(R.id.childaccount_uploader_btn);
        imageGet=(ImageView) findViewById(R.id.image_getter);
        childname= (TextView) findViewById(R.id.childname_createacc);
        UserEmail= (TextView) findViewById(R.id.txt_Email);
        UserNickName= (TextView) findViewById(R.id.txt_NickName);
        String nick_name2=Paper.book().read("Nickname");
        UserNickName.setText("Hello "+ nick_name2);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }
        });

        imageGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }
        });


    }

    private void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    //selection of the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imageGet);
//            imageGet.setImageURI(mImageUri);

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cR= getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void Validate() {
        String Childname= childname.getText().toString().trim();
        if(Childname.isEmpty()){
            childname.setError("Enter your child's name");
        }else{
            Create.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            registerUser();
        }
    }

    private void registerUser() {

        final String email,password,nickename,ImageURL,Cname;
        email= Paper.book().read("Email");
        password=Paper.book().read("Password");
        nickename=Paper.book().read("Nickname");
        Cname=childname.getText().toString().trim();
        ImageURL="test"+"."+getFileExtension(mImageUri);

        StringRequest stringRequest=new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mProgressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CreateChildAcc.this, ParentHomepage.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressBar.setVisibility(View.GONE);
                        Create.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put(KEY_EMAIL,email);
                params.put(KEY_PASS,password);
                params.put(KEY_NICKNAME,nickename);
                params.put(KEY_CHILDNAME,Cname);
                params.put(KEY_CHILDIMAGE,ImageURL);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
