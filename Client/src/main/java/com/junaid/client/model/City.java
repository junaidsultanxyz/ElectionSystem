package com.junaid.client.model;

// @author junaidxyz

import java.util.ArrayList;


public class City {
    private final String name;
    private ArrayList<Division> divisions;

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Division> getDivisions() {
        return divisions;
    }   
}
