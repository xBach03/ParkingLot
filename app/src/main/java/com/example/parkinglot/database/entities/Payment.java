package com.example.parkinglot.database.entities;

import java.time.LocalDateTime;

public class Payment {
    // Primary key
    private int Id;
    private Double Balance;
    private String ValidationDate;
    // Foreign key -> Vehicle
    private int UserId;

    public Payment(Double balance, String validationDate, int userId) {
        Balance = balance;
        ValidationDate = validationDate;
        UserId = userId;
    }

    public int getId() {
        return Id;
    }

    public Double getBalance() {
        return Balance;
    }

    public String getValidationDate() {
        return ValidationDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public void setValidationDate(String validationDate) {
        ValidationDate = validationDate;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
