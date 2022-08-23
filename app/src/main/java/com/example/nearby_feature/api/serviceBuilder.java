package com.example.nearby_feature.api;

import com.google.android.gms.common.api.Api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class serviceBuilder {

    public static apiService buildService(){
        OkHttpClient client=new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/place/").addConverterFactory(GsonConverterFactory.create()).client(client).build();
        apiService service=retrofit.create(apiService.class);
        return service;

    }



}
