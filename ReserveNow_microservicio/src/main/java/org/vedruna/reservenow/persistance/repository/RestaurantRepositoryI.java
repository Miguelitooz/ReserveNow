package org.vedruna.reservenow.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedruna.reservenow.persistance.model.Restaurant;

@Repository
public interface RestaurantRepositoryI extends JpaRepository<Restaurant, Integer> {
    Optional<Restaurant> findByRestaurantName(String restaurantName);
}
