package com.example.nearby_feature.activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.example.nearby_feature.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        Button btn_sign_in = findViewById(R.id.btn_sign_in);
        Button btn_sign_up1 = findViewById(R.id.btn_sign_up1);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signinRegisteredUser();
            }
        });


        btn_sign_up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(SigninActivity.this, "Clicked sign up",
                //Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            //reload();
//        }
//    }


    private void signinRegisteredUser() {
        // [START sign_in_with_email]
        EditText et_signin_email = findViewById(R.id.et_signin_email);
        String email = et_signin_email.getText().toString();

        EditText et_signin_password = findViewById(R.id.et_signin_password);
        String password = et_signin_password.getText().toString();

        if(validateSigninForm(email,password)){
            String temp = "Please Wait";
            showProgressDialog(temp);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");

                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(SigninActivity.this, "Verification done",
                                        Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SigninActivity.this,MainActivity.class);
                                hideProgressDialog();
                                startActivity(intent);
                                finish();

                            } else {
                                hideProgressDialog();
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(SigninActivity.this, "Authentication failed",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }



    private Boolean validateSigninForm(String email ,String password ) {

        if(TextUtils.isEmpty(email)){
            showErrorSnackBar("Please enter email");
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            showErrorSnackBar("Please enter password");
            return false;
        }
        else {
            return true;
        }

    }


//    private void sendEmailVerification() {
//
//        final FirebaseUser user = mAuth.getCurrentUser();
//        user.sendEmailVerification()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // Email sent
//
//                    }
//                });
//
//    }

}