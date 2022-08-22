package com.example.nearby_feature.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.nearby_feature.R;
import com.example.nearby_feature.fragments.dialogFragment;
import com.example.nearby_feature.fragments.feedback;
import com.example.nearby_feature.fragments.mapFragment;
import com.example.nearby_feature.fragments.userActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Dialog dialog;
    NavigationView nav;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize fragment
        toolbar = (Toolbar) findViewById(R.id.toolbar);
                toolbar.setTitle("Jan Dhan Darshak App");
             setSupportActionBar(toolbar);

        mapFragment fragment=new mapFragment();

        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialogbox);

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame,fragment)
                .addToBackStack(null)
                .commit();

        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
           drawerLayout.addDrawerListener(actionBarDrawerToggle);
           actionBarDrawerToggle.syncState();


        nav=findViewById(R.id.slidewindow);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.userActivity:
                        userActivity user=new userActivity();
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.frame,user)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.feedback:
                        feedback fd=new feedback();
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.frame,fd)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    default:
                        getSupportFragmentManager()
                                .beginTransaction().replace(R.id.frame,fragment)
                                .commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }



            }
        });
    }


    public void onBackPressed() {

        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 2 ) {
            // If there are back-stack entries, leave the FragmentActivity
            // implementation take care of them.

            manager.popBackStack();

        } else {
            // Otherwise, ask user if he wants to leave :)
            new AlertDialog.Builder(this)
                    .setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            MainActivity.super.onBackPressed();
                        }
                    }).create().show();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                showDialog();

                return true;
            default:
                return true;

        }


    }

    public void showDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        dialogFragment a=new dialogFragment();
        a.show(fragmentManager,"Dialog Box");
    }
}