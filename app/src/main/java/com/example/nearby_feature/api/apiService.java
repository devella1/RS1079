package com.example.nearby_feature.api;

import com.example.nearby_feature.place;

import retrofit2.*;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apiService {
    @GET("nearybysearch/json?")
    Call<String> getPlace(@Query("location") String latlng  , @Query("type") String nearybyPlace ,@Query("radius") int radius, @Query("language") String language, @Query("key") String api  );

    @GET("nearybysearch/json?")
    Call<String> getVoiceSearch(@Query("location") String latlng  ,@Query("radius") String distance, @Query("keyword") String nearybyPlace , @Query("language") String language, @Query("key") String api  );

    @GET("nearybysearch/json?")
    Call<String> filterByDistance(@Query("location") String latlng , @Query("type") String nearbyPlace , @Query("rankby") String rank, @Query("language") String language, @Query("key") String api  );

    @GET("nearybysearch/json?")
    Call<String> filterByOpenNow(@Query("location") String latlng  ,@Query("opennow") String open, @Query("type") String nearybyPlace , @Query("radius") String distance,@Query("language") String language, @Query("key") String api  );

    @GET("nearybysearch/json?")
    Call<String> filterByOpenNowKeyword(@Query("location") String latlng  , @Query("opennow") String open, @Query("type") String nearybyPlace , @Query("radius") String distance, @Query("language") String language, @Query("key") String api  );

    @GET("nearybysearch/json?")
    Call<String> filterByDistanceKeyword(@Query("keyword") String keyword  , @Query("rankby") String rank , @Query("language") String language, @Query("key") String api  );

    @GET("nearybysearch/json?")
    Call<String> getDistance(@Query("location") String latlng  , @Query("type") String nearybyPlace , @Query("language") String language, @Query("key") String api  );



}
