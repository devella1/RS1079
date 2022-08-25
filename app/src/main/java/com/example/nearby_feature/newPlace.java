package com.example.nearby_feature;

import java.io.Serializable;

public class newPlace  implements Serializable {

    public String name;
    public String desc;
    public String lat;
    public String lon;
    public String type;


    public newPlace(String name, String lat, String lon, String desc,String type){
        this.name=name;
        this.lat=lat;
        this.lon=lon;
        this.desc = desc;
        this.type=type;
    }

}
