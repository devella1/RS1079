package com.example.nearby_feature;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Spinner spType;
    Button btFind;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;
    private final int atm=1;
    private final int bank=2;
    private final int post=3;
    private final int csc=4;
    private final int bankMitra=5;
    private int selected=1;
    private String placeTypeList[] = {"atm", "bank", "hospital"};
    private boolean isfirst=false;
    Dialog dialog;
    NavigationView nav;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        nav=findViewById(R.id.slidewindow);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // will have to write the code to change the fragments over here

                return false;
            }
        });




        dialog=new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialogbox);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        MeowBottomNavigation bn= findViewById(R.id.bottombar);
        bn.add(new MeowBottomNavigation.Model(atm,R.drawable.ic_baseline_atm_24));
        bn.add(new MeowBottomNavigation.Model(bank,R.drawable.ic_baseline_local_post_office_24));
        bn.add(new MeowBottomNavigation.Model(post,R.drawable.ic_baseline_help_24));
        bn.add(new MeowBottomNavigation.Model(csc,R.drawable.ic_baseline_man_24));
        bn.add(new MeowBottomNavigation.Model(bankMitra,R.drawable.ic_baseline_feedback_24));


        bn.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                if(!isfirst) {
                    getSupportFragmentManager().popBackStack();
                }
            }
        });

       bn.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
           @Override

           public void onClickItem(MeowBottomNavigation.Model item) {
               // navigate here
               isfirst=true;
               if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                   getSupportFragmentManager().popBackStack();
               }
               String name;
               switch(item.getId()){
                   case atm:
                       name="ATM";
                       selected=atm;
                       break;
                   case bank:
                       name="BANK";
                       selected=bank;
                       break;
                   case csc:
                       name="hospital"; // since the current code has only the search feature for the hospital , i am setting the remaining options to the name shown
                       selected=atm;
                       break;
                   case post:
                       name="bank";
                       selected=bank;
                       break;
                   case bankMitra:
                       name="bankr";
                       selected=bank;
                       break;
                   default:
                       name="atm";
                       selected=1;

               }

               int i = selected-1;
               String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius=500000    " + "&types=" + placeTypeList[i] + "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key);

               new PlaceTask().execute(url);


           }
       });

        bn.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override

            public void onShowItem(MeowBottomNavigation.Model item) {

                String name;
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
                switch(item.getId()){
                    case atm:
                        name="ATM";
                        selected=atm;
                        break;
                    case bank:
                        name="BANK";
                        selected=bank;
                        break;
                    case csc:
                        name="hospital"; // since the current code has only the search feature for the hospital , i am setting the remaining options to the name shown
                        selected=atm;
                        break;
                    case post:
                        name="bank";
                        selected=bank;
                        break;
                    case bankMitra:
                        name="bankr";
                        selected=bank;
                        break;
                    default:
                        name="atm";
                        selected=1;

                }
            }
        });

        bn.show(atm,true);

        //String placeNameLsit[] = {"ATM", "Bank", "Hospital"};
        //spType.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, placeNameLsit));

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Check permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

     /*  btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = selected-1;
                String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" + "?location=" + currentLat + "," + currentLong + "&radius=50000000" + "&types=" + placeTypeList[i] + "&sensor=true" + "&key=" + getResources().getString(R.string.google_map_key);

               new PlaceTask().execute(url);
            }
        });*/

    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
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
                            LatLng currLocation=new LatLng(currentLat,currentLong);
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 15));
                            CircleOptions circly = new CircleOptions().center(currLocation).radius(5000).fillColor(R.color.teal_700).strokeWidth(0).strokeColor(R.color.teal_700); // in meters
                            Circle circle=map.addCircle(circly);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getCurrentLocation();
            }
        }
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

    private String downloadUrl(String string) throws IOException {

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

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {

            JsonParser jsonParser =new JsonParser();

            List<HashMap<String , String >> mapList= null;
            JSONObject object= null;
            try {
                object= new JSONObject(strings[0]);
                mapList= jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            map.clear();
            StringBuilder st=new StringBuilder();
            TextView t=findViewById(R.id.distances);
            for(int i=0; i<hashMaps.size(); i++)
            {
                HashMap<String, String> hashMapList= hashMaps.get(i);
                double lat= Double.parseDouble(Objects.requireNonNull(hashMapList.get("lat")));
                double lng= Double.parseDouble(Objects.requireNonNull(hashMapList.get("lng")));


                String name= hashMapList.get("name");
                st.append(name+":"+calculateDistanceInKilometer(currentLat,currentLong,lat,lng)+"km\n");
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
                map.addMarker(options);




            }
            LatLng latLng= new LatLng(currentLat, currentLong);

            MarkerOptions options= new MarkerOptions();
            options.position(latLng);
            options.title("Current Position");
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            map.addMarker(options);


            t.setText(String.valueOf(st));
            LatLng currLocation=new LatLng(currentLat,currentLong);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 15));
            CircleOptions circly = new CircleOptions().center(currLocation).radius(5000).fillColor(R.color.teal_700).strokeWidth(0).strokeColor(R.color.teal_700); // in meters
            Circle circle=map.addCircle(circly);
        }
    }


    // method that gets the distance between 2 points when we provide the langitude and latitude
    // used the method directly to get the distance between the touchpoints and curr location


    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public double calculateDistanceInKilometer(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return  (AVERAGE_RADIUS_OF_EARTH_KM * c);
    }

    public void showDialog(View v){
        dialog.show();
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.settings,menu);
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Type here to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch(item.getItemId()){
            case R.id.settings:
                dialog.show();
                return true;
            default:
                return true;

        }
    }





}