package org.vedruna.reservenow.service;

import org.vedruna.reservenow.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    ReviewDTO createReview(String username, Integer restaurantId, String reviewText);
    ReviewDTO updateReview(Integer reviewId, String username, String reviewText);
    void deleteReview(Integer reviewId, String username);
}
