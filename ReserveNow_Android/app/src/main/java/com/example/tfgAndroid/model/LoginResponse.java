package com.example.tfgAndroid.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;

    @SerializedName("username")
    private String username;

    // Constructor
    public LoginResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Empty constructor required for deserialization
    public LoginResponse() {
    }

    // Getter y Setter para token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Getter y Setter para username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
