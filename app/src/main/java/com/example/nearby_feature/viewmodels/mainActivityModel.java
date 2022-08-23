package com.example.nearby_feature.viewmodels;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nearby_feature.R;
import com.example.nearby_feature.activities.MainActivity;
import com.example.nearby_feature.api.apiService;
import com.example.nearby_feature.api.serviceBuilder;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.ServiceOrBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mainActivityModel extends AppCompatActivity {
    String apikey="AIzaSyB4sxrW5vvTZKQCebWquw8rKhyCYnSrlYM";
    public void getData(LatLng curr, String type, int radius ,Context context, GoogleMap map,String language){
        apiService request= serviceBuilder.buildService();
        Call<String> call =request.getPlace("${"+curr.latitude+"},${"+curr.longitude+"}",type,radius,language,apikey);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                    System.out.println(response.raw().request().url());
                    showMarker(jsonParser(response.body()),context,map,curr);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

               // System.out.println(t.message);
                //Toast.makeText(MainActivity.this,"Error in onresponse in getData ",Toast.LENGTH_SHORT).show();
            }
        });


    }

    ArrayList<HashMap<String,String>> jsonParser(String jsondata){
        JSONArray jsonArray=null;
        JSONObject jsonObject;
        try{
            jsonObject = new JSONObject(jsondata);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    ArrayList<HashMap<String,String>> getPlaces(JSONArray jsonArray){
        int placecount=jsonArray.length();
        ArrayList<HashMap<String,String>> placeList= new ArrayList<>();
        HashMap<String,String> placemap;

        for(int i=0;i<placecount;i++){
            try{

                placemap=getPlace(jsonArray.getJSONObject(i));
                placeList.add(placemap);
            }
            catch(JSONException e){
                e.printStackTrace();
                //Toast.makeText(mainActivityModel.this,"Exception occured in getplaces ",Toast.LENGTH_SHORT).show();
            }
        }
        return placeList;

    }

    HashMap<String,String> getPlace(JSONObject placejson) {
        HashMap<String, String> map=new HashMap<>();
        String placename = "-NA-";
        String vicinity = "-NA-";
        String latitude;
        String longitude;
        String reference;
        String isopen;
        try {
            if (!placejson.isNull("name")) {
                placename = placejson.getString("name");
            }
            if (!placejson.isNull("vicinity")) {
                vicinity = placejson.getString("vicinity");

            }
            if (!placejson.isNull("opening_hours") && !placejson.getString("opening_hours").isEmpty()) {
                isopen = placejson.getJSONObject("opening_hours").getString("open_now");
                map.put("isOpen", isopen);
            } else {
                map.put("isOpen", "unknown");
            }

        latitude = placejson.getJSONObject("geometry").getJSONObject("location").getString("lat");
        longitude = placejson.getJSONObject("geometry").getJSONObject("location").getString("lng");
        reference = placejson.getString("place_id");
        map.put("place_name", placename);
        map.put("vicinity", vicinity);
        map.put("lat", latitude);
        map.put("lng", longitude);
        map.put("place_id", reference);
    }
        catch (JSONException e){
            e.printStackTrace();
            //Toast.makeText(mainActivityModel.this,"Error in getplace ",Toast.LENGTH_SHORT).show();
        }

        return  map;
    }

    void showMarker(ArrayList<HashMap<String,String>> list,Context context,GoogleMap map,LatLng curr){
        try{
            map.clear();
            for(int i=0;i< list.size();i++){
                MarkerOptions markerOptions=new MarkerOptions();
                HashMap<String,String> place=list.get(i);
                Double lat=Double.parseDouble(place.get("lat"));
                Double lng=Double.parseDouble(place.get("lng"));
                String placename=place.get("place_name");
                LatLng latLng = new LatLng(lat,lng);
                String placeId=place.get("place_id");

                markerOptions.position(latLng);
                markerOptions.title(placename);
                map.addMarker(markerOptions);
                /*markerOptions.snippet(place.get("vicinity"));

                int height=100;
                int width=100;
                Bitmap bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.marker2);

                Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap,width,height,false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                Marker marker=map.addMarker(markerOptions);
                */






            }
        }
        catch (Exception e){
            e.printStackTrace();
           // Toast.makeText(mainActivityModel.this,"Error in showMarker",Toast.LENGTH_SHORT).show();
        }

        if(!list.isEmpty()){
            try{
                LatLng latLng=new LatLng(Double.parseDouble(list.get(0).get("lat")), Double.parseDouble(list.get(0).get("lng")));
                String placename=list.get(0).get("place_name").toString();
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(10));

            }
            catch (Exception e){
                e.printStackTrace();
                //Toast.makeText(mainActivityModel.this,"Error in listempty in showMarker ",Toast.LENGTH_SHORT).show();
            }
        }

    }


}


