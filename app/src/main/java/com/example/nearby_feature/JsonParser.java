package com.example.nearby_feature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    // I guess , we will need to replace this hashmap with an new object since , it will be good to add more attributes to the place



    private place parseJsonObject(JSONObject object){
        place a=null;
        try {
            String name= object.getString("name");
            String latitude=object.getJSONObject("geometry").getJSONObject("location").getString("lat");
            String longitude=object.getJSONObject("geometry").getJSONObject("location").getString("lng");

            a=new place(name,latitude,longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //return hash map
        return a;

    }


    private List<place> parseJsonArray(JSONArray jsonArray){
        ArrayList<place> dataList =new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++) {
            try {
                place data = parseJsonObject((JSONObject) jsonArray.get(i));
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public List<place> parseResult(JSONObject object){
        JSONArray jsonArray=null;
        try {
            jsonArray= object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJsonArray(jsonArray);

    }
}
