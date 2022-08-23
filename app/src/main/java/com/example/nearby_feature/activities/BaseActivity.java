package com.example.nearby_feature.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.example.nearby_feature.R;
import com.google.android.material.snackbar.Snackbar;


public class BaseActivity extends AppCompatActivity {

    private Dialog mProgressDialog;
    private Boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void showProgressDialog(String text) {
        mProgressDialog = new Dialog(this);

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        mProgressDialog.setContentView(R.layout.dialog_progress);
        TextView tv_progress_text = (TextView) findViewById(R.id.tv_progress_text);



        //Start the dialog and display it on screen.
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

//    public void  showErrorSnackBar(String message) {
//        val snackBar =
//                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
//        val snackBarView = snackBar.view
//        snackBarView.setBackgroundColor(
//                ContextCompat.getColor(
//                        BaseActivity.this,
//        R.color.snackbar_error_color
//            )
//        )
//        snackBar.show()
//    }

}