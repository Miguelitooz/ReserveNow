package org.vedruna.reservenow.persistance.repository;

import org.vedruna.reservenow.dto.ReservationDTO;
import org.vedruna.reservenow.persistance.model.Reservation;
import org.vedruna.reservenow.persistance.model.Restaurant;
import org.vedruna.reservenow.persistance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    // Buscar todas las reservas de un usuario
    List<Reservation> findByUser(User user);

    // Buscar reservas de un usuario con un estado específico (ej. CONFIRMADA)
    List<Reservation> findByUserAndStatus(User user, Reservation.Status status);

    // Buscar todas las reservas de un restaurante
    List<Reservation> findByRestaurant(Restaurant restaurant);

    // Buscar todas las reservas de un usuario con DTO personalizado
    @Query("""
        SELECT new org.vedruna.reservenow.dto.ReservationDTO(
            r.reservationId,
            u.username,
            res.restaurantName,
            t.tableNumber,
            r.reservationDate,
            r.numGuests,
            r.foodAllergies,
            r.status)
        FROM Reservation r
        JOIN r.user u
        JOIN r.restaurant res
        JOIN r.table t
        WHERE u.userId = :userId
    """)
    List<ReservationDTO> findReservationsByUserId(@Param("userId") Integer userId);

    // Buscar reservas por nombre de restaurante, número de mesa y fecha exacta
    @Query("""
        SELECT r FROM Reservation r
        JOIN r.restaurant res
        JOIN r.table t
        WHERE res.restaurantName = :restaurantName
        AND t.tableNumber = :tableNumber
        AND r.reservationDate = :reservationDate
    """)
    List<Reservation> findByRestaurantNameAndTableNumberAndReservationDate(
            @Param("restaurantName") String restaurantName,
            @Param("tableNumber") Integer tableNumber,
            @Param("reservationDate") LocalDateTime reservationDate
    );


    @Query("""
    SELECT r FROM Reservation r
    JOIN r.restaurant res
    JOIN r.table t
    WHERE res.restaurantName = :restaurantName
    AND t.tableNumber = :tableNumber
    """)
    List<Reservation> findByRestaurantNameAndTableNumber(
            @Param("restaurantName") String restaurantName,
            @Param("tableNumber") Integer tableNumber
    );

}
