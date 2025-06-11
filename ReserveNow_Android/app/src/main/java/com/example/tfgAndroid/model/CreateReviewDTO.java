package com.example.tfgAndroid.model;

public class CreateReviewDTO {
    private Long restaurantId;
    private String reviewText;

    public CreateReviewDTO(Long restaurantId, String reviewText) {
        this.restaurantId = restaurantId;
        this.reviewText = reviewText;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getReviewText() {
        return reviewText;
    }
}
