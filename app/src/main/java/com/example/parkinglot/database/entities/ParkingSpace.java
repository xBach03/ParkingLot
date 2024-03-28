package com.example.parkinglot.database.entities;

public class ParkingSpace {
    // Primary key
    private int ParkingId;
    private String Status;
    private String VehicleType;
    private String ParkingSpot;
    private String Area;
    // Foreign key -> Vehicle
    private int VehicleId;

    public ParkingSpace(int parkingId, String status, String vehicleType, String parkingSpot, String area, int vehicleId) {
        ParkingId = parkingId;
        Status = status;
        VehicleType = vehicleType;
        ParkingSpot = parkingSpot;
        Area = area;
        VehicleId = vehicleId;
    }

    public ParkingSpace(String status, String area, String vehicleType, String parkingSpot) {
        Status = status;
        Area = area;
        VehicleType = vehicleType;
        ParkingSpot = parkingSpot;
    }

    public int getParkingId() {
        return ParkingId;
    }

    public void setVehicleType(String vehicleType) {
        VehicleType = vehicleType;
    }

    public void setParkingSpot(String parkingSpot) {
        ParkingSpot = parkingSpot;
    }

    public void setParkingId(int parkingId) {
        ParkingId = parkingId;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setArea(String area) {
        Area = area;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }

    public String isAvailable() {
        return Status;
    }

    public String getArea() {
        return Area;
    }

    public int getVehicleId() {
        return VehicleId;
    }
    public String getVehicleType() {
        return VehicleType;
    }

    public String getParkingSpot() {
        return ParkingSpot;
    }
}
