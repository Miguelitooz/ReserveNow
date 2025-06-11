package org.vedruna.reservenow.persistance.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vedruna.reservenow.persistance.model.Restaurant;
import org.vedruna.reservenow.persistance.model.TableEntity;

@Repository
public interface TableRepositoryI extends JpaRepository<TableEntity, Integer> {

    // Buscar una mesa específica por número y restaurante
    Optional<TableEntity> findByTableNumberAndRestaurant(Integer tableNumber, Restaurant restaurant);

    // Listar todas las mesas de un restaurante
    List<TableEntity> findByRestaurant(Restaurant restaurant);

    // Listar todas las mesas disponibles de un restaurante
    List<TableEntity> findByRestaurantAndAvailableTrue(Restaurant restaurant);

    List<TableEntity> findByRestaurantAndCapacityGreaterThanEqual(Restaurant restaurant, int capacity);

}
