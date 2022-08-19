package com.example.nearby_feature.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.nearby_feature.R;

import com.example.nearby_feature.firebase.FireStoreClass;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class Missing_banks extends AppCompatActivity {

    private Button btn_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_banks);
        btn_submit=findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerBank();
            }
        });
    }

    public void registerBank(){

        EditText  bankname = findViewById(R.id.et_bankname);
        EditText ifsc = findViewById(R.id.et_ifsccode);
        EditText desc = findViewById(R.id.et_description);
        EditText addr = findViewById(R.id.et_bankadress);
        EditText pin = findViewById(R.id.et_pin);
        RadioButton rbYes = findViewById(R.id.radioButtonYes);
        RadioButton rbNo = findViewById(R.id.radioButtonNo);

        Boolean status = false;

        if(rbYes.isSelected()){
            status = true;
        }
        else if(rbNo.isSelected()){
            status = false;
        }


        String st_bankname = bankname.getText().toString();
        String st_ifsc = ifsc.getText().toString();
        String st_desc = desc.getText().toString();
        String st_addr = addr.getText().toString();
        String st_pin= pin.getText().toString();

        FireStoreClass.addMissingBank(st_bankname,st_ifsc,st_desc,st_addr,st_pin,status,this);
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