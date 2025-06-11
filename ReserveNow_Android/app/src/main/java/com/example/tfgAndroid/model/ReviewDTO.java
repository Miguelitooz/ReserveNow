package com.example.tfgAndroid.model;

import com.google.gson.annotations.SerializedName;

public class ReviewDTO {
    @SerializedName("reviewId")
    private Long reviewId;

    @SerializedName("username")
    private String username;

    @SerializedName("restaurantName")
    private String restaurantName;

    @SerializedName("reviewText")
    private String reviewText;

    // getters
    public Long getReviewId() {
        return reviewId;
    }

    public String getUsername() {
        return username;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getReviewText() {
        return reviewText;
    }
}
