package com.junaid.server.model;

// @author junaidxyz

import java.util.ArrayList;


public class City {
    private final String name;
    private ArrayList<Division> divisions;

    public City(String name) {
        this.name = name;
    }

    public City(String name, ArrayList<Division> divisions) {
        this.name = name;
        this.divisions = divisions;
    }
    
    

    public String getName() {
        return name;
    }

    public ArrayList<Division> getDivisions() {
        return divisions;
    }   
}
