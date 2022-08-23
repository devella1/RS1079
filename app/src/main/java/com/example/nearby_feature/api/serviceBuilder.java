package com.example.nearby_feature.api;

import com.google.android.gms.common.api.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class serviceBuilder {

    public static final String url="https://maps.googleapis.com/maps/api/place/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson=new GsonBuilder().create();

    private static Retrofit.Builder retroBuilder = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson));


    public static <S> S buildService(Class<S> apiService){
        Retrofit retrofit=retroBuilder.client(httpClient.build()).build();
        return retrofit.create(apiService);
    }
    /*public static apiService buildService(){
        OkHttpClient.Builder client=new OkHttpClient.Builder();
        client.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://maps.googleapis.com/maps/api/place/").addConverterFactory(GsonConverterFactory.create()).client(client).build();
        apiService service=retrofit.create(apiService.class);
        return service;

    }
    */




}
