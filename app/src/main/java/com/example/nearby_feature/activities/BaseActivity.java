package com.example.nearby_feature.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.nearby_feature.R;
import com.google.android.material.snackbar.Snackbar;


public class BaseActivity extends AppCompatActivity {

    public Dialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void showProgressDialog(String text) {
        mProgressDialog = new Dialog(BaseActivity.this);
        mProgressDialog.setContentView(R.layout.dialog_progress);
        TextView tv_progress_text = mProgressDialog.findViewById(R.id.tv_progress_text);
        tv_progress_text.setText(text);
        mProgressDialog.show();
    }



    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    public void  showErrorSnackBar(String message) {
        Snackbar snackBar =
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);


        View snackBarView = snackBar.getView();

        snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                        BaseActivity.this,
        R.color.snackbar_error_color
            )
        );
        snackBar.show();
    }

}