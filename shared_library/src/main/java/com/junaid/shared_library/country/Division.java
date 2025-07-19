package com.junaid.shared_library.country;

// @author junaidxyz

import java.io.Serializable;


public class Division implements Serializable {
    private final int id;

    public Division(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
