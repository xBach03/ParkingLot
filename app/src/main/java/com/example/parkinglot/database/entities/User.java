package com.example.parkinglot.database.entities;

public class User {
    // Primary key
    private int Id;
    private String UserName;
    private String Password;

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
