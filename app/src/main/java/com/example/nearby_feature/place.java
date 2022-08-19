package com.example.nearby_feature;

public class place {
    private String name;
    private String lat;
    private String lang;

    public place(String name, String lat, String lang){
        this.name=name;
        this.lat=lat;
        this.lang=lang;
    }

    public String getLang() {
        return lang;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }
}
