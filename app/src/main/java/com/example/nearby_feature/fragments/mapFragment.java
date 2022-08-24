package com.example.nearby_feature.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.nearby_feature.JsonParser;
import com.example.nearby_feature.R;
import com.example.nearby_feature.activities.MainActivity;
import com.example.nearby_feature.place;
import com.example.nearby_feature.viewmodels.mainActivityDataProvider;
import com.example.nearby_feature.viewmodels.mainActivityModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class mapFragment extends Fragment {

    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;
    private final int atm=1;
    private final int bank=2;
    private final int post=3;
    private final int csc=4;
    private final int bankMitra=5;
    private int selected=1;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    private View view;
    private EditText editText;
    private TextView tv1;
    private TextView tv2;

    private mainActivityDataProvider provider;
    private LatLng currLocation;
    //private String placeTypeList[] = {"atm", "bank", "hospital"};
    private String deviceLanguage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        view=inflater.inflate(R.layout.fragment_map, container, false);

        deviceLanguage = Locale.getDefault().getLanguage();
        //View v=inflater.inflate(R.layout.fragment_map,container,false);

        MeowBottomNavigation bn= view.findViewById(R.id.bottombar);
        bn.add(new MeowBottomNavigation.Model(atm, R.drawable.ic_baseline_atm_24));
        bn.add(new MeowBottomNavigation.Model(bank, R.drawable.ic_baseline_local_post_office_24));
        bn.add(new MeowBottomNavigation.Model(post, R.drawable.ic_baseline_help_24));
        bn.add(new MeowBottomNavigation.Model(csc, R.drawable.ic_baseline_man_24));
        bn.add(new MeowBottomNavigation.Model(bankMitra, R.drawable.ic_baseline_feedback_24));
        // Initialize map fragment
        supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map



        // Return view

        bn.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override

            public void onClickItem(MeowBottomNavigation.Model item) {
                // navigate here

                String name;
                switch(item.getId()){
                    case atm:
                        name="ATM";
                        selected=atm;
                        //if(!model.getData(currLocation,"atm",1000,view.getContext(),map,deviceLanguage)){
                        //    Toast.makeText(view.getContext(),"not able to parse data",Toast.LENGTH_SHORT).show();
                        //}
                        break;
                    case bank:
                        name="BANK";
                        selected=bank;

                        break;
                    case csc:
                        name="hospital";
                        selected=atm;

                        break;
                    case post:
                        name="bank";
                        selected=bank;

                        break;
                    case bankMitra:
                        name="bankr";
                        selected=bank;
                        break;
                    default:
                        name="atm";
                        selected=1;

                }


                // this process will make the requests each time is not good , it must be changed
                //int i = selected-1;
                //String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius=500000    " + "&types=" + placeTypeList[i] + "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key);

                //new PlaceTask().execute(url);

                mainActivityDataProvider dataProvider = new mainActivityDataProvider();
                provider=dataProvider;
                dataProvider.setMap(map);
                dataProvider.findPlacesAccordingToDistance(currentLat,currentLong,5000,selected-1,getResources().getString(R.string.google_map_key));

            }
        });


        bn.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override

            public void onShowItem(MeowBottomNavigation.Model item) {

                String name;

                switch(item.getId()){
                    case atm:
                        name="ATM";
                        break;
                    case bank:
                        name="BANK";
                        break;
                    case csc:
                        name="hospital";
                        break;
                    case post:
                        name="bank";
                        break;
                    case bankMitra:
                        name="bankr";
                        break;
                    default:
                        name="atm";

                }
            }
        });

        bn.show(atm,true);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        //Check whether the user has provided the current location permission using the below code
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }



        //editText=view.findViewById(R.id.edit_text);
        //tv1= view.findViewById(R.id.tv1);
        // tv2=view.findViewById(R.id.tv2);

        //Initialize Places


        // ----- edit text code over here

        Places.initialize(getActivity().getApplicationContext(), "AIzaSyB4sxrW5vvTZKQCebWquw8rKhyCYnSrlYM");

        //Set EditText non focusable
       /* editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PlaceField List
                // will have to upade this
                List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.OPENING_HOURS, Place.Field.PHONE_NUMBER, Place.Field.RATING, Place.Field.USER_RATINGS_TOTAL, Place.Field.ID);

                //Create intent
                Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getActivity());

                //Start activity result
                startActivityForResult(intent, 100);
            }
        });
        */

        ToggleButton toggleButton = view.findViewById(R.id.filterByOpenNow);



        toggleButton.setText(getResources().getString(R.string.toggleOff));
        toggleButton.setTextOff(getResources().getString(R.string.toggleOff));
        toggleButton.setTextOn(getResources().getString(R.string.toggleOff));

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton.isChecked()){
                    provider.filterPlacesByOpenNow(currentLat,currentLong,5000,selected-1,getResources().getString(R.string.google_map_key));
                    toggleButton.setBackgroundColor(getResources().getColor(R.color.forestgreen));
                    toggleButton.setTextColor(getResources().getColor(R.color.white));
                }
                else{

                    provider.findPlacesAccordingToDistance(currentLat,currentLong,5000,selected-1,getResources().getString(R.string.google_map_key));
                    toggleButton.setBackgroundColor(getResources().getColor(R.color.white));
                    toggleButton.setTextColor(getResources().getColor(R.color.black));

                }
            }
        });



        return view;
    }

/*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode== getActivity().RESULT_OK){
            //Initialize place

            Place place =Autocomplete.getPlaceFromIntent(data);

            editText.setText(place.getAddress());
            String pid= place.getId();
            tv1.setText(String.format("Locality Name: %s\n Phone Number: %s\nPlace ID: %s\n Ratings: %s\n User ratings total %s\n\n  Opening hours %s\n",place.getName(), place.getPhoneNumber(), pid, place.getRating(),  place.getUserRatingsTotal(), place.getOpeningHours()));
            tv2.setText(String.valueOf(place.getLatLng()));
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status= Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getActivity().getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }


 */
    // ------ edit text code ends over here okay



    private void getCurrentLocation() {

        // return when the user has not provided the location permissions

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding

            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        // this is the code which does the needed thing when the location of the current place is provided
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!= null){
                    currentLat=location.getLatitude();
                    currentLong=location.getLongitude();
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            map =googleMap;
                            currLocation=new LatLng(currentLat,currentLong);
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 15));
                            CircleOptions circly = new CircleOptions().center(currLocation).radius(1000).fillColor(R.color.purple_700).strokeWidth(0).strokeColor(R.color.teal_700); // in meters
                            Circle circle=map.addCircle(circly);

                        }
                    });
                }
            }
        });
    }



    // this method is only for getting current location and has no relation with the places api

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // when you get the current location permissions , run the getCurrentLocation() command , which will animate the camera and add marker to the place

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getCurrentLocation();
            }
        }
    }





}
