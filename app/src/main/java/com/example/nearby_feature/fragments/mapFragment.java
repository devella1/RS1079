package com.example.nearby_feature.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.widget.ToggleButton;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.nearby_feature.JsonParser;
import com.example.nearby_feature.R;
import com.example.nearby_feature.activities.BaseActivity;
import com.example.nearby_feature.activities.MainActivity;
import com.example.nearby_feature.activities.MapMissingBank;
import com.example.nearby_feature.adapter;
import com.example.nearby_feature.detailActivity;
import com.example.nearby_feature.firebase.FireStoreClass;
import com.example.nearby_feature.newPlace;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.maps.model.MapStyleOptions;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;



/*
public class MapsActivityRaw extends AppCompatActivity
implements OnMapReadyCallback {

    private static final String TAG = MapsActivityRaw.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.fragment_map);

        // Get the SupportMapFragment and register for the callback
        // when the map is ready for use.
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);
    }
}
*/

public class mapFragment extends Fragment {

    FusedLocationProviderClient fusedLocationProviderClient;
    // public Dialog mProgressDialog;
    ToggleButton toggleButton;
    public static double currentLat = 0, currentLong = 0;
    private final int atm=1;
    private final int bank=2;
    private final int post=3;
    private final int csc=4;
    private final int bankMitra=5;
    public static  int selected=1;
    SupportMapFragment supportMapFragment;
    public static GoogleMap map;
    private View view;
    private EditText editText;
    private TextView tv1;
    private TextView tv2;
    public static  List<place> placeList;
    private mainActivityModel model;
    public static  mainActivityDataProvider provider=new mainActivityDataProvider();
    public static  LatLng currLocation;
    private String deviceLanguage;
    private MaterialButton showListButton;
    private Button showFirstButton;
    private adapter listAdapter;
    private BottomSheetBehavior bottomSheetBehavior;
    private FloatingActionButton setting;
    private FloatingActionButton voiceSearch;
    private FloatingActionButton textSearch;
    public static int BufferDistance=1000;
    public static String theme="STANDARD";
    private EditText editTextForSearch;
    private Button searchButton;
    dialogFragment a=new dialogFragment();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        view=inflater.inflate(R.layout.fragment_map, container, false);

        //provider.setMap(map);
        deviceLanguage = Locale.getDefault().getLanguage();
        //View v=inflater.inflate(R.layout.fragment_map,container,false);
        if(provider!=null) {
            placeList = provider.getPlaceList();
        }

        setting=view.findViewById(R.id.settings);
        voiceSearch=view.findViewById(R.id.voicesearch);
        textSearch=view.findViewById(R.id.textsearch);
        editTextForSearch=view.findViewById(R.id.keyword);
        searchButton=view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=(String)editTextForSearch.getText().toString();
                String[] arr=text.split(" ");
                if(arr.length>=1 && arr[0]!="") {
                    placeList = provider.findPlacesAccordingToKeyword(currentLat, currentLong, BufferDistance, 8, arr[0], getResources().getString(R.string.google_map_key));
                    editTextForSearch.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(getActivity(),"Enter your keywords ",Toast.LENGTH_SHORT).show();
                }


            }
        });
        voiceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startVoiceRecognitionActivity();
            }
        });

        textSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextForSearch.setVisibility(view.getVisibility());
                searchButton.setVisibility(view.getVisibility());
            }
        });





        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                a.show(fragmentManager,"Dialog Box");

               // FireStoreClass.addToFav("sitTozVwBzQ5NjLZYDGuFGnwQQD3","1236");












            }
        });


        showListButton= view.findViewById(R.id.showList);
        showFirstButton=view.findViewById(R.id.showfirstItem);
        showFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(placeList!=null){

                    Intent a=new Intent(getActivity(), detailActivity.class);
                    a.putExtra("placeId",placeList.get(0).getId());
                    a.putExtra("lat",placeList.get(0).getLat());
                    a.putExtra("lang",placeList.get(0).getLang());
                    startActivity(a);
                }

            }
        });

        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showList(view);

            }
        });
//        View v=view.findViewById(R.id.bottomsheet);
//        bottomSheetBehavior=BottomSheetBehavior.from(v);
        model=new mainActivityModel();
        MeowBottomNavigation bn= view.findViewById(R.id.bottombar);
        bn.add(new MeowBottomNavigation.Model(atm, R.drawable.ic_baseline_atm_24));
        bn.add(new MeowBottomNavigation.Model(bank, R.drawable.banks));
        bn.add(new MeowBottomNavigation.Model(post, R.drawable.ic_baseline_local_post_office_24));
        bn.add(new MeowBottomNavigation.Model(csc, R.drawable.csc));
        bn.add(new MeowBottomNavigation.Model(bankMitra, R.drawable.bankmitra));
        // Initialize map fragment
        supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map

        toggleButton = view.findViewById(R.id.filterByOpenNow);

        // Return view
        bn.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override

            public void onClickItem(MeowBottomNavigation.Model item) {
                // navigate here
                // showProgressDialog("please wait");
                String name;
                switch(item.getId()){
                    case atm:

                        selected=atm;
                        toggleButton.setChecked(false);
                        changeState();
                        break;
                    case bank:
                        name="BANK";
                        selected=bank;
                        toggleButton.setChecked(false);
                        changeState();
                        break;
                    case csc:
                        name="hospital";
                        selected=csc;
                        toggleButton.setChecked(false);
                        changeState();
                        break;
                    case post:
                        name="bank";
                        selected=post;
                        toggleButton.setChecked(false);
                        changeState();
                        break;
                    case bankMitra:
                        name="bankr";
                        selected=bankMitra;
                        toggleButton.setChecked(false);
                        changeState();
                        break;

                    default:
                        name="atm";
                        toggleButton.setChecked(false);
                        selected=1;
                        changeState();

                }

                ArrayList<String> dbTypes = new ArrayList<String>() {
                    {
                        add("Atm");
                        add("Bank");
                        add("PostOffice");
                        add("Csc");
                        add("BankMitra");
                    }
                };

                provider.setMap(map);
                provider.plot(currLocation,1000,dbTypes.get(selected-1));


//
//                for(int i=0; i<ds.size(); i++)
//                {
//
//
//                    Toast.makeText(getActivity(),ds.get(i).name, Toast.LENGTH_SHORT).show();
//
//                }




                if(selected<=3) {
                    placeList=provider.findPlacesAccordingToDistance(currentLat, currentLong,  BufferDistance, selected - 1, getResources().getString(R.string.google_map_key));

                }
                else if(selected==4){
                    placeList=provider.findPlacesAccordingToKeyword(currentLat,currentLong,BufferDistance,selected-1,"csc",getResources().getString(R.string.google_map_key));

                }
                else{
                    Toast.makeText(getActivity(),"no data to show ",Toast.LENGTH_SHORT).show();
                    provider.clearMap();
                }



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

        //bn.show(atm,true);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        //Check whether the user has provided the current location permission using the below code
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        toggleButton.setText(getResources().getString(R.string.toggleOff));
        toggleButton.setTextOff(getResources().getString(R.string.toggleOff));
        toggleButton.setTextOn(getResources().getString(R.string.toggleOff));

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(toggleButton.isChecked()){


                    if(provider==null || map==null){
                        provider.setMap(map);
                        placeList=provider.filterPlacesByOpenNow(currentLat,currentLong,BufferDistance,0,getResources().getString(R.string.google_map_key));
                        //placeList=provider.getPlaceList();
                        changeState();

                        Toast.makeText(getActivity(),"Please select navbar before filter",Toast.LENGTH_SHORT).show();

                        //hideProgressDialog();
                    }
                    else{
                        if(selected<=3) {
                            placeList = provider.filterPlacesByOpenNow(currentLat, currentLong, BufferDistance, selected - 1, getResources().getString(R.string.google_map_key));
                        }
                        else if(selected==4){
                            placeList= provider.findOpenPlacesAccordingToKeyword(currentLat, currentLong, BufferDistance, selected - 1,"csc", getResources().getString(R.string.google_map_key));
                        }
                        else{
                            Toast.makeText(getActivity(),"no data to show ",Toast.LENGTH_SHORT).show();
                            provider.clearMap();
                        }
                        changeState();
                        //hideProgressDialog();
                    }



                }
                else{
                    if(selected<=3) {
                        placeList = provider.findPlacesAccordingToDistance(currentLat, currentLong, 5000, selected - 1, getResources().getString(R.string.google_map_key));
                    }
                    else if(selected==4){
                        placeList= provider.findPlacesAccordingToKeyword(currentLat, currentLong, 5000, selected - 1,"csc", getResources().getString(R.string.google_map_key));
                    }
                    else{
                        Toast.makeText(getActivity(),"no data to show ",Toast.LENGTH_SHORT).show();
                        provider.clearMap();
                    }
                    changeState();
                    //hideProgressDialog();


                }
            }
        });



//        provider.setMap(map);
//        placeList=provider.findPlacesAccordingToDistance(currentLat, currentLong, 5000, 0, getResources().getString(R.string.google_map_key));


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
                            provider.setMap(map);
                            if(theme.equals("STANDARD")) {
                                boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.standard_style_json));
                            }
                            else if(theme.equals("DARK")){
                                boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.dark_style_json));
                            }
                            else if(theme.equals("RETRO")){
                                boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.retro_style_json));
                            }
                            else if(theme.equals("SILVER")){
                                boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.silver_style_json));
                            }
                            else{
                                boolean success = map.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.aubergine_style_json));
                            }
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

    public void changeState(){
        if(toggleButton.isChecked()){
            toggleButton.setBackgroundColor(getResources().getColor(R.color.forestgreen));
            toggleButton.setTextColor(getResources().getColor(R.color.white));
        }
        else{
            toggleButton.setBackgroundColor(getResources().getColor(R.color.white));
            toggleButton.setTextColor(getResources().getColor(R.color.black));
        }
    }

//    public void showProgressDialog(String text) {
//        mProgressDialog = new Dialog(getActivity());
//        mProgressDialog.setContentView(R.layout.dialog_progress);
//        TextView tv_progress_text = mProgressDialog.findViewById(R.id.tv_progress_text);
//        tv_progress_text.setText(text);
//        mProgressDialog.show();
//    }
//
//    public void hideProgressDialog() {
//        mProgressDialog.dismiss();
//    }

    public void showList(View v){
        if(placeList!=null) {

            FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);


            listFragment fg= new listFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("list", (Serializable) placeList);
            fg.setArguments(bundle);

            ft.replace(R.id.frame,fg);
            ft.addToBackStack(null);
            ft.commit();

        }
        else{
            Toast.makeText(getActivity(),"no list present ",Toast.LENGTH_SHORT).show();
        }


    }


    public void showDialog(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        dialogFragment a=new dialogFragment();
        a.show(fragmentManager,"Dialog Box");
    }


    private void startVoiceRecognitionActivity(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"");
        startActivityForResult(intent,1234);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1234 && resultCode == Activity.RESULT_OK){
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String match=matches.get(0);
            placeList= provider.findPlacesAccordingToKeyword(currentLat,currentLong,BufferDistance,8,match,getResources().getString(R.string.google_map_key));

        }
    }









}