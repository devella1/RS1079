package com.example.nearby_feature.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.nearby_feature.*;
import com.example.nearby_feature.firebase.FireStoreClass;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);
        CardView splashcard=findViewById(R.id.splashcard);
        splashcard.startAnimation(animation);

        Thread thread =new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);

                }
                catch(Exception E){
                    E.printStackTrace();

                }
                finally{

                    String curUserId = FireStoreClass.getCurrentUserID();

                    Intent i;

                    if(curUserId!=null){

                        i = new Intent(splashScreen.this, MainActivity.class);
                    }
                    else{
                        i = new Intent(splashScreen.this, SigninActivity.class);
                    }

                    startActivity(i);
                    finish();


                }
                super.run();
            }
        };
        thread.start();
    }
}
