package com.example.cartrade;

<<<<<<< HEAD
=======
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

>>>>>>> cad4b1ca35011c6815ab39f706066818b7e68a25
public class Car {
    private String name;
    private int price;
    private String first_registration;
    private int ps;
    private int kilometres;
    private String description;
    private String location;
    private String telNumber;

<<<<<<< HEAD
=======
    public Car(String name, int price, String first_registration, int ps, int kilometres, String description, String location, String telNumber) {
        this.name = name;
        this.price = price;
        this.first_registration = first_registration;
        this.ps = ps;
        this.kilometres = kilometres;
        this.description = description;
        this.location = location;
        this.telNumber = telNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFirst_registration() {
        return first_registration;
    }

    public void setFirst_registration(String first_registration) {
        this.first_registration = first_registration;
    }

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
>>>>>>> cad4b1ca35011c6815ab39f706066818b7e68a25
}
