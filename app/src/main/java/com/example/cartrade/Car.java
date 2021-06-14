package com.example.cartrade;


import android.widget.ImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Car {
    private String name;
    private double price;
    private String first_registration;
    private int ps;
    private int kilometres;
    private String description;
    private String location;
    private String telNumber;
    //private ImageView imageView;


    public Car(String name, double price, String first_registration, int ps, int kilometres, String description, String location, String telNumber/*,ImageView imageView*/) {
        this.name = name;
        this.price = price;
        this.first_registration = first_registration;
        this.ps = ps;
        this.kilometres = kilometres;
        this.description = description;
        this.location = location;
        this.telNumber = telNumber;
        //this.imageView = imageView;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFirst_registration() {
        return first_registration;
    }

    public void setFirst_registration(String first_registration) {
        this.first_registration = first_registration;
    }

    /*public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }*/

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getKilometres() {
        return kilometres;
    }

    public void setKilometres(int kilometres) {
        this.kilometres = kilometres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    @Override
    public String toString() {
        return name + ";" + price + ";" + first_registration + ";" + ps + ";" + kilometres + ";" + description  + ";" + location + ";" + telNumber;
    }
    public Date getFirstRegistration() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            return sdf.parse(first_registration);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return new Date(first_registration);
    }

    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", this.name);
        result.put("price", this.price);
        result.put("first_registration", this.first_registration);
        result.put("ps", this.ps);
        result.put("kilometres", this.kilometres);
        result.put("description", this.description);
        result.put("location", this.location);
        result.put("telNumber", this.telNumber);
        //result.put("imageView", this.imageView);
        return result;
    }

}
