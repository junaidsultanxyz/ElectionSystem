package com.junaid.server.model;

// @author junaidxyz

public class Voter {
    private String cnic;
    private String name;
    private int age;
    private Division division;
    private String password;

    public Voter(String cnic, String name, int age, Division division, String password) {
        this.cnic = cnic;
        this.name = name;
        this.age = age;
        this.division = division;
        this.password = password;
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

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
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
