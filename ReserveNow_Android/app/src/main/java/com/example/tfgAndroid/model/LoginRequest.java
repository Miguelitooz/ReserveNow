package com.example.tfgAndroid.model;


public class LoginRequest {
    private String name;
    private String password;

    // Constructor
    public LoginRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
