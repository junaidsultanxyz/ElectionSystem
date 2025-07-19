package com.junaid.server.model;

// @author junaidxyz

import java.io.Serializable;


public class Voter implements Serializable{
    private String cnic;
    private String name;
    private int age;
    private int division;
    private String password;

    public Voter(String cnic, String name, int age, int division, String password) {
        this.cnic = cnic;
        this.name = name;
        this.age = age;
        this.division = division;
        this.password = password;
    }

    public Voter(String cnic, String name, int division) {
        this.cnic = cnic;
        this.name = name;
        this.division = division;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDivision() {
        return division;
    }

    public void setDivision(int division) {
        this.division = division;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Voter{" + "cnic=" + cnic + ", name=" + name + ", age=" + age + ", division=" + division + ", password=" + password + '}';
    }
}
