package com.example.parkinglot.database.entities;

import java.time.LocalDateTime;

public class Transaction {
    // Primary key
    private int TransactionId;
    private Double amount;
    private LocalDateTime time;

    // Foreign key refers to users
    private String fromPaymentId;
    private String toPaymentId;

    public Transaction() {

    }

    public Transaction(int transactionId, Double amount, LocalDateTime time, String fromPaymentId, String toPaymentId) {
        TransactionId = transactionId;
        this.amount = amount;
        this.time = time;
        this.fromPaymentId = fromPaymentId;
        this.toPaymentId = toPaymentId;
    }

    public int getTransactionId() {
        return TransactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getFromPaymentId() {
        return fromPaymentId;
    }

    public String getToPaymentId() {
        return toPaymentId;
    }

    public void setTransactionId(int transactionId) {
        TransactionId = transactionId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setFromPaymentId(String fromPaymentId) {
        this.fromPaymentId = fromPaymentId;
    }

    public void setToPaymentId(String toPaymentId) {
        this.toPaymentId = toPaymentId;
    }
}
