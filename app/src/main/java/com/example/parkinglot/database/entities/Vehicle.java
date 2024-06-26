package com.example.parkinglot.database.entities;

public class Vehicle {
    // Primary key
    private String LicensePlate;
    private String Type;
    private String Color;
    // Foreign key -> User
    private int UserId;

    public Vehicle(String licensePlate, int userId) {
        LicensePlate = licensePlate;
        UserId = userId;
    }

    public Vehicle(String licensePlate, String type, String color, int userId) {
        LicensePlate = licensePlate;
        Type = type;
        Color = color;
        UserId = userId;
    }


    public String getLicensePlate() {
        return LicensePlate;
    }

    public String getType() {
        return Type;
    }

    public String getColor() {
        return Color;
    }

    public int getUserId() {
        return UserId;
    }


    public void setLicensePlate(String licensePlate) {
        LicensePlate = licensePlate;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setColor(String color) {
        Color = color;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
