package com.junaid.shared_library.country;

// @author junaidxyz

import java.io.Serializable;
import java.util.ArrayList;


public class City implements Serializable {
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
