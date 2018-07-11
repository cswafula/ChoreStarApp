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

public class AddChore extends AppCompatActivity {
    public static final String CHORE_URL="https://chorestar95049.herokuapp.com/api/NewChore";
    public static final String KEY_EMAIL="Email";
    public static final String KEY_CHORENAME="ChoreName";
    public static final String KEY_CHOREIMAGE="ChoreImage";
    public static final String KEY_CHOREPOINTS="ChorePoints";
    public static final String KEY_CHILDNAME="ChildName";


    private Spinner spinner;
    private ArrayList<Avatar> avatarList;
    private AvatarAdapter avatarAdapter;
    String Chore_Image = "";
    EditText ChildName,ChoreName,Points;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chore);
        spinner=findViewById(R.id.NewChoreAvatar);
        ChildName=findViewById(R.id.newChore_Childname);
        ChoreName=findViewById(R.id.newChore_ChoreName);
        Points=findViewById(R.id.newChore_ChorePoints);
        add=findViewById(R.id.newChore_Save);

        Paper.init(this);

        initList();
        avatarAdapter = new AvatarAdapter(this, avatarList);
        spinner.setAdapter(avatarAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Avatar ClickedAvatar = (Avatar) parent.getItemAtPosition(position);
                int ClickedImage = ClickedAvatar.getAvatarImage();
                Chore_Image = String.valueOf(ClickedImage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate();
            }
        });
    }

    private void Validate() {
        String image,child_name,chore_points,chore_name;
        image=Chore_Image;
        child_name=ChildName.getText().toString().trim();
        chore_points=Points.getText().toString().trim();
        chore_name=ChoreName.getText().toString().trim();
        if(child_name.isEmpty()){
            ChildName.setError("Child Name is Required");
        }else if(chore_name.isEmpty()){
            ChoreName.setError("Chore Name is Required");
        }else if(chore_points.isEmpty()){
            Points.setError("Chore ponts are Required");
        }else if(image.isEmpty()){
            Toast.makeText(getApplicationContext(),"Select Chore Image First",Toast.LENGTH_LONG).show();
        }else{
            int points=Integer.valueOf(chore_points);
            if(points<=0 || points>=100){
                Points.setError("Invalid number of Points");
            }else{
                addChore();
            }
        }
    }

    private void addChore() {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Adding New Chore...");
        progressDialog.show();

        final String fetch_Email,fetch_image,fetch_child_name,fetch_chore_points,fetch_chore_name;
        fetch_Email= Paper.book().read("LoginEmail").toString();
        fetch_image=Chore_Image;
        fetch_child_name=ChildName.getText().toString().trim();
        fetch_chore_points=Points.getText().toString().trim();
        fetch_chore_name=ChoreName.getText().toString().trim();


        StringRequest stringRequest=new StringRequest(Request.Method.POST, CHORE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
                        finish();
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
                params.put(KEY_EMAIL,fetch_Email);
                params.put(KEY_CHILDNAME,fetch_child_name);
                params.put(KEY_CHOREIMAGE,fetch_image);
                params.put(KEY_CHORENAME,fetch_chore_name);
                params.put(KEY_CHOREPOINTS,fetch_chore_points);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void initList() {
        avatarList = new ArrayList<>();
        avatarList.add(new Avatar(R.drawable.chore1));
        avatarList.add(new Avatar(R.drawable.chore2));
        avatarList.add(new Avatar(R.drawable.chore3));
        avatarList.add(new Avatar(R.drawable.chore4));
        avatarList.add(new Avatar(R.drawable.chore5));
    }
}
