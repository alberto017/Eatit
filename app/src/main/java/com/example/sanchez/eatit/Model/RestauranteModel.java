package com.example.sanchez.eatit.Model;

public class RestauranteModel {

    private String Name;
    private String Image;
    private String Address;
    private String City;
    private String Schedule;
    private String Description;

    public RestauranteModel() {

    }//RestauranteModel

    public RestauranteModel(String name, String image, String address, String city, String schedule, String description) {
        Name = name;
        Image = image;
        Address = address;
        City = city;
        Schedule = schedule;
        Description = description;
    }//RestauranteModel

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getSchedule() {
        return Schedule;
    }

    public void setSchedule(String schedule) {
        Schedule = schedule;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
