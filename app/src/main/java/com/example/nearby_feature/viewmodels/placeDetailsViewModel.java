package com.example.nearby_feature.viewmodels;

import android.app.DownloadManager;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.*;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class placeDetailsViewModel extends ViewModel {
    Place placeDetail;
    public void getDetails(String id , PlacesClient client, ImageView placePhoto){
        List<Place.Field> fieldList= Arrays.asList(Place.Field.ADDRESS, Place.Field.NAME, Place.Field.OPENING_HOURS, Place.Field.PHONE_NUMBER, Place.Field.RATING, Place.Field.USER_RATINGS_TOTAL, Place.Field.ID, Place.Field.LAT_LNG);

        FetchPlaceRequest request=FetchPlaceRequest.newInstance(id,fieldList);

        client.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {
                Place place= response.getPlace();
                placeDetail=place;

            }
        });
    }
}
