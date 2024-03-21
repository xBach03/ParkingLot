package com.example.parkinglot.database.entities;

public class ParkingSpace {
    // Primary key
    private int ParkingId;
    private boolean Availability;
    private String VehicleType;
    private String Area;
    // Foreign key -> Vehicle
    private int VehicleId;

    public ParkingSpace(boolean availability, String area, int vehicleId, String vehicleType) {
        Availability = availability;
        Area = area;
        VehicleId = vehicleId;
        VehicleType = vehicleType;
    }

    public ParkingSpace(boolean availability, String area, String vehicleType) {
        Availability = availability;
        Area = area;
        VehicleType = vehicleType;
    }

    public int getParkingId() {
        return ParkingId;
    }

    public void setParkingId(int parkingId) {
        ParkingId = parkingId;
    }

    public void setAvailability(boolean availability) {
        Availability = availability;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public boolean isAvailability() {
        return Availability;
    }

    public String getArea() {
        return Area;
    }

    public int getVehicleId() {
        return VehicleId;
    }
}
