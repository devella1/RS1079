package com.example.nearby_feature.viewmodels;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.PhotoMetadata;
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
                getPhoto(place,client,placePhoto);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
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
