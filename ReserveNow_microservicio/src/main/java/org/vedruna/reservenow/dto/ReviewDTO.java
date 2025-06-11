package org.vedruna.reservenow.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private Integer restaurantId;
    private String username;           // opcional para listar
    private String restaurantName;     // opcional para listar
    private String reviewText;
    private LocalDateTime createdAt;

    // getters & setters
    public Integer getReviewId() { return reviewId; }
    public void setReviewId(Integer reviewId) { this.reviewId = reviewId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Integer restaurantId) { this.restaurantId = restaurantId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
