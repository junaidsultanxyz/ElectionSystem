package com.junaid.server.model;

// @author junaidxyz

import java.util.ArrayList;


public class Province {
    private final String code;
    private final String name;
    private ArrayList<City> cities;

    public Province(String code, String name) {
        this.code = code;
        this.name = name;
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
