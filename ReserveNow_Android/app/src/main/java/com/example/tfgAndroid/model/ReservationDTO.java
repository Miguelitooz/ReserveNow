package com.example.tfgAndroid.model;

public class ReservationDTO {

    private Integer reservationId;
    private String username;
    private String restaurantName;
    private Integer tableNumber;
    private String reservationDate;  // fecha como String ISO8601, ej. "2025-05-27T14:30:00"
    private int numGuests;
    private String foodAllergies;
    private Status status;

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED
        // Añade otros estados si tienes más en el backend
    }

    public ReservationDTO() {
    }

    public ReservationDTO(Integer reservationId, String username, String restaurantName, Integer tableNumber,
                          String reservationDate, int numGuests, String foodAllergies, Status status) {
        this.reservationId = reservationId;
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.reservationDate = reservationDate;
        this.numGuests = numGuests;
        this.foodAllergies = foodAllergies;
        this.status = status;
    }



    // Getters y setters

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getNumGuests() {
        return numGuests;
    }

    public void setNumGuests(int numGuests) {
        this.numGuests = numGuests;
    }

    public String getFoodAllergies() {
        return foodAllergies;
    }

    public void setFoodAllergies(String foodAllergies) {
        this.foodAllergies = foodAllergies;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
