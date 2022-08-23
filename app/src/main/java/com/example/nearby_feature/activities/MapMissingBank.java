package com.example.nearby_feature.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nearby_feature.R;
import com.example.nearby_feature.fragments.MissingBanksMapFragment;

public class MapMissingBank extends AppCompatActivity {

    Button btn_ok;
    MissingBanksMapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_missing_bank);

        //Initialize fragment
        fragment=new MissingBanksMapFragment();

        //Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout,fragment)
                .addToBackStack(null)
                .commit();

        btn_ok = findViewById(R.id.btn_ok);

//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MapMissingBank.this,Missing_banks.class);
//                startActivity(intent);
//            }
//        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fragment.latitude!=null && fragment.longitude!=null){
                    Intent intent = new Intent(MapMissingBank.this,Missing_banks.class);
                    intent.putExtra("latitude",fragment.latitude);
                    intent.putExtra("longitude",fragment.longitude);
                    startActivity(intent);
                    finish();
                }
                else{

                    Toast.makeText(MapMissingBank.this, "Select a place first",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}