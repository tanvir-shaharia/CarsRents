package com.example.carsrent;

public class Post_Model {
    String picture,name,time,carmodel,rent_permont;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCarmodel() {
        return carmodel;
    }

    public void setCarmodel(String carmodel) {
        this.carmodel = carmodel;
    }

    public String getRent_permont() {
        return rent_permont;
    }

    public void setRent_permont(String rent_permont) {
        this.rent_permont = rent_permont;
    }

    public Post_Model(String picture, String name, String time, String carmodel, String rent_permont) {
        this.picture = picture;
        this.name = name;
        this.time = time;
        this.carmodel = carmodel;
        this.rent_permont = rent_permont;
    }

    public Post_Model() {
    }
}
