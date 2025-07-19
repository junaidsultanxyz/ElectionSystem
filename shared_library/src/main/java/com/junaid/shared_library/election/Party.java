package com.junaid.shared_library.election;

// @author junaidxyz

import java.io.Serializable;


public class Party implements Serializable{
    private String code;
    private String name;
    private String flagImagePath;
    private String symbolImagePath;

    public Party(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Party(String code, String name, String flagImagePath, String symbolImagePath) {
        this.code = code;
        this.name = name;
        this.flagImagePath = flagImagePath;
        this.symbolImagePath = symbolImagePath;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagImagePath() {
        return flagImagePath;
    }

    public void setFlagImagePath(String flagImagePath) {
        this.flagImagePath = flagImagePath;
    }

    public String getSymbolImagePath() {
        return symbolImagePath;
    }

    public void setSymbolImagePath(String symbolImagePath) {
        this.symbolImagePath = symbolImagePath;
    }
    
    
}
