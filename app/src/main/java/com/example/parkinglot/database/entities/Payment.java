package com.example.parkinglot.database.entities;

import java.time.LocalDateTime;

public class Payment {
    // Primary key
    private int Id;
    private String Amount;
    private LocalDateTime Time;
    // Foreign key -> Vehicle
    private int VehicleId;

    public Payment(String amount, LocalDateTime time, int vehicleId) {
        Amount = amount;
        Time = time;
        VehicleId = vehicleId;
    }

    public int getId() {
        return Id;
    }

    public String getAmount() {
        return Amount;
    }

    public LocalDateTime getTime() {
        return Time;
    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public void setTime(LocalDateTime time) {
        Time = time;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }
}
