package com.example.tfgAndroid.model;

import java.util.List;

public class ReservationRequest {
    private String restaurantName;
    private String reservationDate;
    private int numGuests;
    private String foodAllergies;

    public ReservationRequest(String restaurantName, String reservationDate, int numGuests, String foodAllergies) {
        this.restaurantName = restaurantName;
        this.reservationDate = reservationDate;
        this.numGuests = numGuests;
        this.foodAllergies = foodAllergies;
    }

    // Getters y setters si los necesitas
}
