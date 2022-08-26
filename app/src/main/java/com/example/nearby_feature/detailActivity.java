package com.example.nearby_feature;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nearby_feature.viewmodels.placeDetailsViewModel;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.OpeningHours;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class detailActivity extends AppCompatActivity {

    private static final String TAG = "";
    Intent i;
    String placeId;
    ImageView placePhoto;
    TextView placeName;
    TextView placeType;
    ImageButton placeDirections;
    ImageButton placeCall;
    ImageButton favourites;
    ImageButton share;
    TextView address;
    TextView placeTimings;
    TextView placeMissingInfo;
    Place place;
    Place place1;
    String lat;
    String lang;
    //public placeDetailsViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        i = getIntent();
        // model = new placeDetailsViewModel();
        placeId = i.getStringExtra("placeId");
        lat=i.getStringExtra("lat");
        lang=i.getStringExtra("lang");
        placePhoto = findViewById(R.id.placePhoto);
        placeName = findViewById(R.id.placeName);
        placeType = findViewById(R.id.placeType);
        placeDirections = findViewById(R.id.placeDirections);
        placeCall = findViewById(R.id.placeCall);
        favourites = findViewById(R.id.placeSave);
        share = findViewById(R.id.placeShare);
        placeType = findViewById(R.id.placeType);
        address = findViewById(R.id.placeAddress);
        placeTimings = findViewById(R.id.placeTimings);
        placeMissingInfo = findViewById(R.id.placeMissingInfo);



// Specify the fields to return.
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.OPENING_HOURS,
                Place.Field.PHONE_NUMBER,
                Place.Field.PHOTO_METADATAS,
                Place.Field.TYPES,
                Place.Field.LAT_LNG);

// Construct a request object, passing the place ID and fields array.
        final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        Places.initialize(getApplicationContext(), getResources().getString(R.string.google_map_key), Locale.US);

        PlacesClient placesClient = Places.createClient(this);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            place = response.getPlace();
            placeName.setText(place.getName());
            address.setText(place.getAddress());
            getPhoto(place,placesClient,placePhoto);
            placeTimings.setText(isOpen(place.getOpeningHours()));
            Toast.makeText(detailActivity.this,place.getName(),Toast.LENGTH_SHORT).show();
            Log.i(TAG, "Place found: " + place.getName());
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                final ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + exception.getMessage());
                final int statusCode = apiException.getStatusCode();

            }
        });


        placeDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Uri  IntentUrl= Uri.parse("google.navigation:q=" + lat + "," + lang);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                mapIntent.setData(IntentUrl);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uri  IntentUrl= Uri.parse("google.navigation:q=" + lat + "," + lang);
                String Url=lat+","+lang;
                Intent a=new Intent(Intent.ACTION_SEND);
                a.setType("text/plain");
                a.putExtra(Intent.EXTRA_TEXT,Url);
                Intent b=Intent.createChooser(a,"Send Message via........");
                startActivity(b);
            }
        });




    }

    public String isOpen(OpeningHours hours){
        String a="";
        for(int i=0;i<7;i++){
            a+=hours.getWeekdayText().get(i);
            a+="\n";
        }
        return a;
    }


    private void getPhoto(Place place, PlacesClient placesClient,ImageView placePhoto){
        List<PhotoMetadata> data= place.getPhotoMetadatas();
        if(data==null || data.isEmpty()){
        }
        else{
            PhotoMetadata photoMetadata=data.get(0);

            FetchPhotoRequest photoRequest=FetchPhotoRequest.builder(photoMetadata).setMaxHeight(250).setMaxWidth(400).build();

            placesClient.fetchPhoto(photoRequest).addOnSuccessListener(new OnSuccessListener<FetchPhotoResponse>() {
                @Override
                public void onSuccess(FetchPhotoResponse fetchPhotoResponse) {
                    Bitmap bitmap= fetchPhotoResponse.getBitmap();
                    placePhoto.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }
    }


}