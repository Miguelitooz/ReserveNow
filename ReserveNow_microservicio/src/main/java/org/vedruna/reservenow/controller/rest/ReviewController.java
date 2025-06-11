package org.vedruna.reservenow.controller.rest;

import org.vedruna.reservenow.dto.ReviewDTO;
import org.vedruna.reservenow.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired 
    private ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> listAll() {
        return reviewService.getAllReviews();
    }

    public static class CreateReviewRequest {
        public Integer restaurantId;
        public String reviewText;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreateReviewRequest req) {
        ReviewDTO created = reviewService.createReview(
            userDetails.getUsername(),
            req.restaurantId,
            req.reviewText
        );
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    public static class UpdateReviewRequest {
        public String reviewText;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer id,
            @Valid @RequestBody UpdateReviewRequest req) {
        ReviewDTO updated = reviewService.updateReview(
            id,
            userDetails.getUsername(),
            req.reviewText
        );
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer id) {
        reviewService.deleteReview(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
