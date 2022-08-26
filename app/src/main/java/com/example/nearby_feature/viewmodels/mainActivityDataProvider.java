package com.example.nearby_feature.viewmodels;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.nearby_feature.JsonParser;
import com.example.nearby_feature.R;
import com.example.nearby_feature.activities.BaseActivity;
import com.example.nearby_feature.activities.MainActivity;
import com.example.nearby_feature.place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class mainActivityDataProvider  {

    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    private GoogleMap map;
    private Double currentLat;
    private Double currentLong;
    private String placeTypeList[] = {"atm", "bank", "post_office"};
    private int selected;

    private int radius ;
    private List<place> placeList;








    public void clearMap(){
        map.clear();
    }

    public List<place> getPlaceList(){
        return placeList;
    }
    public void setMap(GoogleMap map){
        this.map=map;
    }
    //String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius=500000    " + "&types=" + placeTypeList[i] + "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key);
    public List<place> findPlacesAccordingToDistance(double currentLat, double currentLong, int radius, int  type, String key){
        //obj.showProgressDialog("Please Wait");
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius="+radius+"&types=" + placeTypeList[type]+ "&sensor=true" + "&key=" + key;
        this.currentLat=currentLat;
        this.currentLong=currentLong;
        this.selected=type+1;
        this.radius=radius;
        new PlaceTask().execute(url);

        return placeList;
    }

    public List<place> filterPlacesByOpenNow(double currentLat , double currentLong , int radius , int type , String key){
       // obj.showProgressDialog("Please Wait");
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius="+radius+"&types=" + placeTypeList[type]+ "&sensor=true" + "&key=" + key+"&opennow";
        this.currentLat=currentLat;
        this.currentLong=currentLong;
        this.selected=type+1;
        this.radius=radius;
        new PlaceTask().execute(url);
        return placeList;
    }

    public List<place> findPlacesAccordingToKeyword(double currentLat, double currentLong, int radius , int type , String keyword , String key ){
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius="+radius+"&keyword=" +keyword+ "&sensor=true" + "&key=" + key;
        this.currentLat=currentLat;
        this.currentLong=currentLong;
        this.selected=type+1;
        this.radius=radius;
        new PlaceTask().execute(url);
        return placeList;
    }

    public List<place> findOpenPlacesAccordingToKeyword(double currentLat, double currentLong,int radius , int type , String keyword,String key){
        String url="https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius="+radius+"&keyword=" +keyword+ "&sensor=true" + "&key=" + key+"&opennow";
        this.currentLat=currentLat;
        this.currentLong=currentLong;
        this.selected=type+1;
        this.radius=radius;
        new PlaceTask().execute(url);
        return placeList;
    }



    private class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data=null;
            try {
                data=downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //Execcute Parser Task
            new ParserTask().execute(s);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<place>> {
        @Override
        // we get the get the information about the locations over here
        protected List<place> doInBackground(String... strings) {

            JsonParser jsonParser =new JsonParser();

            List<place> mapList= null;
            JSONObject object= null; // check about this
            try {
                object= new JSONObject(strings[0]);
                mapList= jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            placeList=mapList;
            return mapList;
        }


        // check about this


        protected void onPostExecute(List<place> arr) {

            map.clear();
            StringBuilder st=new StringBuilder();
            ArrayList<MarkerOptions> markers =new ArrayList<>();
            //TextView t=view.findViewById(R.id.distances);
            placeList=arr;
            for(int i=0; i<arr.size(); i++)
            {
                place p= arr.get(i);
                double lat= Double.parseDouble(p.getLat());
                double lng= Double.parseDouble(p.getLang());


                String name= p.getName();
                // st.append(name+":"+calculateDistanceInKilometer(currentLat,currentLong,lat,lng)+"km\n");
                LatLng latLng= new LatLng(lat, lng);
                MarkerOptions options= new MarkerOptions();
                options.position(latLng);
                options.title(name);


                if(selected==1)
                {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                }
                else if(selected==2)
                {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                }
                else if(selected==3)
                {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                }
                else if(selected==4)
                {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                }

                map.addMarker(options);
                markers.add(options);

            }

            LatLng latLng= new LatLng(currentLat, currentLong);

            MarkerOptions options= new MarkerOptions();
            options.position(latLng);
            options.title("Current Position");
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            map.addMarker(options);

            //setting bounds to automate zoom level for presenting all markers on visible screen

            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (MarkerOptions marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();

            int padding=16;




            LatLng currLocation=new LatLng(currentLat,currentLong);
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map.animateCamera(cu);
//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 15));
            CircleOptions circly = new CircleOptions().center(currLocation).radius(radius).fillColor(R.color.purple_700).strokeWidth(0).strokeColor(R.color.purple_700); // in meters
            Circle circle=map.addCircle(circly);

            //obj.hideProgressDialog();
        }
    }


    private String downloadUrl(String string) throws IOException {
        // This thing can be used for both the places api and the nearby search api

        URL url=new URL(string);
        HttpURLConnection connection =(HttpURLConnection) url.openConnection();
        connection.connect();
        InputStream stream=connection.getInputStream();
        BufferedReader reader= new BufferedReader(new InputStreamReader(stream));
        StringBuilder builder =new StringBuilder();
        String line="";
        while((line= reader.readLine())!=null)
        {
            builder.append(line);
        }

        String data= builder.toString();
        reader.close();

        return data;
    }

    public double calculateDistanceInKilometer(double userLat, double userLng,
                                               double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist=  (AVERAGE_RADIUS_OF_EARTH_KM * c);
        dist=(double)Math.round(dist*1000d)/1000d;
        return dist;
    }

}
