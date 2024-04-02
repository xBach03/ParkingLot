package com.example.parkinglot.database.entities;

import java.time.LocalDateTime;

public class HistoryManager {
    // Primary key
    private int HistoryId;
    private LocalDateTime StartTime;
    private LocalDateTime EndTime;
    private double MoneyPaid;
    // Foreign key -> User
    private int UserId;
    // Foreign key -> Vehicle
    private int VehicleId;

    public HistoryManager(LocalDateTime startTime, LocalDateTime endTime, double moneyPaid, int userId, int vehicleId) {
        StartTime = startTime;
        EndTime = endTime;
        MoneyPaid = moneyPaid;
        UserId = userId;
        VehicleId = vehicleId;
    }

    public int getHistoryId() {
        return HistoryId;
    }

    public LocalDateTime getStartTime() {
        return StartTime;
    }

    public LocalDateTime getEndTime() {
        return EndTime;
    }

    public double getMoneyPaid() {
        return MoneyPaid;
    }

    public int getUserId() {
        return UserId;
    }

    public int getVehicleId() {
        return VehicleId;
    }

    public void setHistoryId(int historyId) {
        HistoryId = historyId;
    }

    public void setStartTime(LocalDateTime startTime) {
        StartTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        EndTime = endTime;
    }

    public void setMoneyPaid(double moneyPaid) {
        MoneyPaid = moneyPaid;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setVehicleId(int vehicleId) {
        VehicleId = vehicleId;
    }
}
