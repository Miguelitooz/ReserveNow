package org.vedruna.reservenow.service;

import org.vedruna.reservenow.dto.ReviewDTO;
import org.vedruna.reservenow.persistance.model.Review;
import org.vedruna.reservenow.persistance.model.User;
import org.vedruna.reservenow.persistance.model.Restaurant;
import org.vedruna.reservenow.persistance.repository.ReviewRepository;
import org.vedruna.reservenow.persistance.repository.UserRepositoryI;
import org.vedruna.reservenow.persistance.repository.RestaurantRepositoryI;
import org.vedruna.reservenow.service.ReviewService;
import org.vedruna.reservenow.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired private ReviewRepository reviewRepo;
    @Autowired private UserRepositoryI userRepo;
    @Autowired private RestaurantRepositoryI restaurantRepo;

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepo.findAll().stream()
                         .map(this::toDTO)
                         .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO createReview(String username, Integer restaurantId, String reviewText) {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Restaurant rest = restaurantRepo.findById(restaurantId)
            .orElseThrow(() -> new ResourceNotFoundException("Restaurant", "id", restaurantId));

        Review r = new Review();
        r.setUser(user);
        r.setRestaurant(rest);
        r.setReviewText(reviewText);

        return toDTO(reviewRepo.save(r));
    }

    @Override
    public ReviewDTO updateReview(Integer reviewId, String username, String reviewText) {
        Review r = reviewRepo.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        // opcional: solo permites editar tu propia reseña
        if (! r.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("No autorizado a editar esta reseña");
        }

        r.setReviewText(reviewText);
        return toDTO(reviewRepo.save(r));
    }

    @Override
    public void deleteReview(Integer reviewId, String username) {
        Review r = reviewRepo.findById(reviewId)
            .orElseThrow(() -> new ResourceNotFoundException("Review", "id", reviewId));

        if (! r.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("No autorizado a borrar esta reseña");
        }

        reviewRepo.delete(r);
    }

    private ReviewDTO toDTO(Review r) {
        ReviewDTO d = new ReviewDTO();
        d.setReviewId(      r.getReviewId() );
        d.setUserId(        r.getUser().getUserId().intValue() );
        d.setRestaurantId(  r.getRestaurant().getRestaurantId().intValue() );
        d.setUsername(      r.getUser().getUsername() );
        d.setRestaurantName(r.getRestaurant().getRestaurantName());
        d.setReviewText(    r.getReviewText() );
        d.setCreatedAt(     r.getCreatedAt() );
        return d;
    }
}