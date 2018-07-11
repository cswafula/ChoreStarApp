package com.example.charlie.chorestarapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import io.paperdb.Paper;

public class ParentHomepage extends AppCompatActivity
        implements  BottomNavigationView.OnNavigationItemSelectedListener{

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_homepage);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        Paper.init(this);

        Fragment fragment1 = new ProfileFragment();
        loadFragment(fragment1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;
        switch (item.getItemId()){
            case R.id.nav_chores:
                fragment = new ChoresFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_alerts:
                fragment = new AlertsFragment();
                break;
            case R.id.nav_friends:
                fragment = new FriendsFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment !=null){
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_friends:
                Fragment fragment2 = new FriendsFragment();
                return loadFragment(fragment2);
            case R.id.nav_logout:
                logout();
                break;
            case R.id.nav_switch:
                finish();
                startActivity(new Intent(ParentHomepage.this,ChildSplash.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        final android.support.v7.app.AlertDialog.Builder builder= new android.support.v7.app.AlertDialog.Builder(ParentHomepage.this);
        builder.setMessage("Are you sure you want to Logout?");
        builder.setCancelable(true);
        builder.setNegativeButton("No, Stay!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes, Logout!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String empty="";
                Paper.book().write("LoginEmail",empty);
                Paper.book().write("LoginPassword",empty);
                Paper.book().write("LoginNickName",empty);
                Paper.book().write("LoginStatus",empty);
                finish();
                startActivity(new Intent(ParentHomepage.this, LoginPage.class));
            }
        });
        android.support.v7.app.AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
       if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}
