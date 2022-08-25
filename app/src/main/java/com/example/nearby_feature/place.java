package com.example.nearby_feature;

public class place {
    private String name;
    private String lat;
    private String lang;
    private String id;

    public place(String name, String lat, String lang, String id){
        this.name=name;
        this.lat=lat;
        this.lang=lang;
        this.id=id;
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

    public String getId(){
        return id;
    }
}
