package com.example.cartrade;

public class CarEntity {
    private int id;
    private String car_string;

    public CarEntity(int id, String car_string) {
        this.car_string = car_string;
        this.id = id;
    }

    public CarEntity() {
    }

    public String getCar_string() {
        return car_string;
    }

    public void setCar_string(String car_string) {
        this.car_string = car_string;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
