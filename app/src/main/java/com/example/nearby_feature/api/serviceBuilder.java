package com.example.nearby_feature.api;

import com.squareup.okhttp.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class serviceBuilder {

    public apiService buildService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/place/").addConverterFactory(ScalarsConverterFactory.create()).build();
        apiService service=retrofit.create(apiService.class);
        return service;

    }



}
