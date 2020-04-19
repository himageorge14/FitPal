package com.fitpal.fitpal.model;

public class Restaurant {
    String name;
    String address;
    String costForTwo;

    public Restaurant() {
    }

    public Restaurant(String name, String address, String costForTwo) {
        this.name = name;
        this.address = address;
        this.costForTwo = costForTwo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCostForTwo() {
        return costForTwo;
    }

    public void setCostForTwo(String costForTwo) {
        this.costForTwo = costForTwo;
    }
}
