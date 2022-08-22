package com.example.nearby_feature.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.nearby_feature.R;


public class dialogFragment extends DialogFragment {



    SeekBar seekbar;
    TextView bufferText;

    @Override
    @NonNull
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =requireActivity().getLayoutInflater();

        View v=inflater.inflate(R.layout.dialogbox,null);
        builder.setView(v);

        AlertDialog alertDialog= builder.create();

        seekbar=v.findViewById(R.id.seekbar);

        bufferText=v.findViewById(R.id.buffervalue);

        bufferText.setText(seekbar.getProgress()+"/"+seekbar.getMax());



        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progress=i;
                bufferText.setText(seekbar.getProgress()+"/"+seekbar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //bufferText.setText(seekbar.getProgress()+"/"+seekbar.getMax());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                bufferText.setText(seekbar.getProgress()+"/"+seekbar.getMax());

            }
        });




        return alertDialog;


    }
}