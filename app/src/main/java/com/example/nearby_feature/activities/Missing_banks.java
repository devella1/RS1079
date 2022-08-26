package com.example.nearby_feature.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.nearby_feature.R;

import com.example.nearby_feature.firebase.FireStoreClass;
import com.example.nearby_feature.fragments.MissingBanksMapFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.Objects;

public class Missing_banks extends BaseActivity {

    private Button btn_submit;
    private Button btn_chooseMap;


    public String st_lat;
    public  String st_lon;


  // MissingBanksMapFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_banks);
        btn_submit=findViewById(R.id.btn_submit);
        btn_chooseMap = findViewById(R.id.btn_choseMap);

         //Initialize fragment
//        fragment=new MissingBanksMapFragment();
//
//         //Open fragment
//        getSupportFragmentManager()
//                .beginTransaction().replace(R.id.frame_layout,fragment)
//                .commit();

        btn_chooseMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Missing_banks.this,MapMissingBank.class);
                startActivity(intent);
                finish();
            }
        });

        EditText et_lat = findViewById(R.id.et_lat);
        EditText et_lon = findViewById(R.id.et_lon);

        if(getIntent().getStringExtra("latitude")!=null){
            st_lat  = getIntent().getStringExtra("latitude");
        }

        if(getIntent().getStringExtra("longitude")!=null){
            st_lon = getIntent().getStringExtra("longitude");
        }


        if(st_lat!=null && st_lon!=null){
            et_lat.setText(st_lat);
            et_lon.setText(st_lon);
        }





        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(st_lat!=null && st_lon!=null){
                   // Toast.makeText(Missing_banks.this, "All Ok",
                            //Toast.LENGTH_SHORT).show();

                    registerBank();
                }
                else{

                    Toast.makeText(Missing_banks.this, "Please choose the place on map",
                            Toast.LENGTH_SHORT).show();
                }

                //registerBank();
            }
        });

    }

    public void registerBank(){

        EditText  name = findViewById(R.id.et_name);
        EditText desc = findViewById(R.id.et_description);
        RadioButton rbAtm = findViewById(R.id.radioButtonAtm);
        RadioButton rbBank = findViewById(R.id.radioButtonBank);
        RadioButton rbBnkMitra = findViewById(R.id.radioButtonBnkMitra);
        RadioButton rbPo = findViewById(R.id.radioButtonPostOffice);
        RadioButton rbCsc = findViewById(R.id.radioButtonCsc);
        String latitude = st_lat;
        String longitude = st_lon;


        String type = "Atm";

        if(rbAtm.isChecked()){
            type="Atm";
            Log.d("Col", "Atm");
        }
        else if(rbBank.isChecked()){
            type="Bank";

        }
        else if(rbBnkMitra.isChecked())
        {
            type="BankMitra";

        }
        else if(rbPo.isChecked()){
            type="PostOffice";
        }
        else if(rbCsc.isChecked()){
            type="Csc";
            Log.d("Col", "CSC");
        }


        String st_name = name.getText().toString();
        String st_desc = desc.getText().toString();

        String curUserId = FireStoreClass.getCurrentUserID();


        Toast.makeText(this,curUserId,Toast.LENGTH_SHORT).show();
        if(Objects.equals(curUserId, "")){
            Toast.makeText(this,"Please login first",Toast.LENGTH_SHORT).show();
        }
        else if(Objects.equals(curUserId, "RQqQa4QlEmak5Q7ZUX1abvGLxV22")){
            Toast.makeText(this,"admin",Toast.LENGTH_SHORT).show();
            FireStoreClass.addMissingBank(st_name,st_desc,latitude,longitude,type,this,true);
            Log.d("Col", type);
        }
        else{
            Toast.makeText(this,"user",Toast.LENGTH_SHORT).show();
            FireStoreClass.addMissingBank(st_name,st_desc,latitude,longitude,type,this,false);
        }

    }



    public OnSuccessListener<? super DocumentReference> addSuccess() {

        //hideProgressDialog()
        Toast.makeText(this,"Thanks for reaching us",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
        return null;
    }
}