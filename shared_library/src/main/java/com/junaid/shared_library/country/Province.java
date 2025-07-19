package com.junaid.shared_library.country;

// @author junaidxyz

import java.io.Serializable;
import java.util.ArrayList;


public class Province implements Serializable{
    private final String code;
    private final String name;
    private ArrayList<City> cities;

    public Province(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Province(String code, String name, ArrayList<City> cities) {
        this.code = code;
        this.name = name;
        this.cities = cities;
    }
    
    

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ArrayList<City> getCities() {
        return cities;
    }
}
