package com.example.parkinglot.database.entities;

import java.time.LocalDateTime;

public class Reservation {
    // Primary key
    private int ReservationId;
    private LocalDateTime reservedTime;
    private LocalDateTime startTime;
    // Foreign key -> User
    private int UserId;
    // Foreign key -> Parkingspace
    private int ParkingId;

    public Reservation(int reservationId, LocalDateTime reservedTime, LocalDateTime startTime, int userId, int parkingId) {
        ReservationId = reservationId;
        this.reservedTime = reservedTime;
        this.startTime = startTime;
        UserId = userId;
        ParkingId = parkingId;
    }

    public Reservation(LocalDateTime time, int userId, int parkingId) {
        this.reservedTime = time;
        UserId = userId;
        ParkingId = parkingId;
    }

    public int getReservationId() {
        return ReservationId;
    }

    public LocalDateTime getReservedTime() {
        return reservedTime;
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

    public void setReservedTime(LocalDateTime reservedTime) {
        this.reservedTime = reservedTime;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }


    public void setParkingId(int parkingId) {
        ParkingId = parkingId;
    }
}
