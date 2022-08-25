package com.example.nearby_feature.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nearby_feature.R;
import com.example.nearby_feature.activities.MainActivity;
import com.example.nearby_feature.activities.SigninActivity;
import com.example.nearby_feature.activities.SignupActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;


public class signin extends Fragment {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    View view;

    public static signin newInstance() {
        signin fragment = new signin();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_signin, container, false);

        mAuth = FirebaseAuth.getInstance();

        Button btn_sign_in = view.findViewById(R.id.btn_sign_in);
        Button btn_sign_up1 = view.findViewById(R.id.btn_sign_up1);

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

                Intent intent = new Intent(getActivity(),SignupActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }


    private void signinRegisteredUser() {

        EditText et_signin_email = view.findViewById(R.id.et_signin_email);
        String email = et_signin_email.getText().toString();

        EditText et_signin_password = view.findViewById(R.id.et_signin_password);
        String password = et_signin_password.getText().toString();

        Toast.makeText(getActivity(), email,
                                        Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getActivity(), "Verification done",
                                        Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(getActivity(), "Authentication failed",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


//        if(validateSigninForm(email,password)){
//            String temp = "Please Wait";
            //showProgressDialog(temp);

//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Log.d(TAG, "signInWithEmail:success");
//
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                Toast.makeText(getActivity(), "Verification done",
//                                        Toast.LENGTH_SHORT).show();
//
////                                Intent intent = new Intent(getActivity(), MainActivity.class);
////                                //hideProgressDialog();
////                                startActivity(intent);
//
//
//                            } else {
//                                //hideProgressDialog();
//                                Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                //Toast.makeText(SigninActivity.this, "Authentication failed",
//                                        //Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
//        }
    }



    private Boolean validateSigninForm(String email ,String password ) {

        if(TextUtils.isEmpty(email)){
            //showErrorSnackBar("Please enter email");
            return false;
        }
        else if(TextUtils.isEmpty(password)){
            //showErrorSnackBar("Please enter password");
            return false;
        }
        else {
            return true;
        }

    }


}