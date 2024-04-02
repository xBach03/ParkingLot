package com.example.parkinglot.database.entities;

public class User {
    // Primary key
    private int Id;
    // Username is unique
    private String UserName;
    private String Password;

    public User(int id, String userName, String password) {
        Id = id;
        UserName = userName;
        Password = password;
    }

    public User(String userName, String password) {
        this.UserName = userName;
        this.Password = password;
    }

    public void setId(int id){
        this.Id = id;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public int getId(){
        return this.Id;
    }

    public String getUserName() {
        return this.UserName;
    }

    public String getPassword() {
        return this.Password;
    }
}
