package com.example.parkinglot.database.entities;

import java.time.LocalDateTime;

public class Reservation {
    // Primary key
    private int ReservationId;
    private LocalDateTime time;
    // Foreign key -> User
    private int UserId;
    // Foreign key -> Parkingspace
    private int ParkingId;

    public Reservation(LocalDateTime time, int userId, int parkingId) {
        this.time = time;
        UserId = userId;
        ParkingId = parkingId;
    }

    public int getReservationId() {
        return ReservationId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getUserId() {
        return UserId;
    }


    public int getParkingId() {
        return ParkingId;
    }

    public void setReservationId(int reservationId) {
        ReservationId = reservationId;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }


    public void setParkingId(int parkingId) {
        ParkingId = parkingId;
    }
}
