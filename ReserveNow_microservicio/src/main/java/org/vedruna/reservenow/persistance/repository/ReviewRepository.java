package org.vedruna.reservenow.persistance.repository;

import org.vedruna.reservenow.persistance.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
