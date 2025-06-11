package com.example.tfgAndroid.model;

import com.google.gson.annotations.SerializedName;

public class UserDTO {

    @SerializedName("userId")
    private int id;

    @SerializedName("username")
    private String name;

    private String email;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }


}
