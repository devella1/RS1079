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
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.example.nearby_feature.R;
import com.google.android.gms.maps.model.MapStyleOptions;


public class dialogFragment extends DialogFragment {



    SeekBar seekbar;
    TextView bufferText;
    int bufferDistance=10;
    Spinner spinner;
    Spinner spinnerForLanguages ;



    @Override
    @NonNull
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState){
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater =requireActivity().getLayoutInflater();

        View v=inflater.inflate(R.layout.dialogbox,null);
        builder.setView(v);

        AlertDialog alertDialog= builder.create();

        seekbar=v.findViewById(R.id.seekbar);

        spinner=v.findViewById(R.id.Themes);
        bufferText=v.findViewById(R.id.buffervalue);

        bufferText.setText(seekbar.getProgress()+"/"+seekbar.getMax());
        spinnerForLanguages=v.findViewById(R.id.Spinner);



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
                bufferDistance=((seekbar.getProgress()/4))*1000;
                mapFragment.BufferDistance=bufferDistance;

                if(mapFragment.selected<=3) {
                    mapFragment.placeList=mapFragment.provider.findPlacesAccordingToDistance(mapFragment.currentLat, mapFragment.currentLong, mapFragment.BufferDistance, mapFragment.selected - 1, getResources().getString(R.string.google_map_key));

                }
                else if(mapFragment.selected==4){
                    mapFragment.placeList=mapFragment.provider.findPlacesAccordingToKeyword(mapFragment.currentLat,mapFragment.currentLong,mapFragment.BufferDistance,mapFragment.selected-1,"csc",getResources().getString(R.string.google_map_key));

                }
                else{
                    Toast.makeText(getActivity(),"no data to show ",Toast.LENGTH_SHORT).show();
                    mapFragment.provider.clearMap();
                }

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mapFragment.theme=(String)spinner.getSelectedItem();
                if(mapFragment.theme.equals("STANDARD")) {
                    boolean success = mapFragment.map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.standard_style_json));
                }
                else if(mapFragment.theme.equals("DARK")){
                    boolean success = mapFragment.map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.dark_style_json));
                }
                else if(mapFragment.theme.equals("RETRO")){
                    boolean success = mapFragment.map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.retro_style_json));
                }
                else if(mapFragment.theme.equals("SILVER")){
                    boolean success = mapFragment.map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.silver_style_json));
                }
                else{
                    boolean success = mapFragment.map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.aubergine_style_json));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerForLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        return alertDialog;


    }



}