package com.example.nearby_feature.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.nearby_feature.*;

public class splashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //getSupportActionBar().hide();

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoomout);
        Animation animation1=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blinkanimation);
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
                    Intent i=new Intent(splashScreen.this,MainActivity.class);
                    startActivity(i);
                    finish();

                }
                super.run();
            }
        };
        thread.start();
    }
}
