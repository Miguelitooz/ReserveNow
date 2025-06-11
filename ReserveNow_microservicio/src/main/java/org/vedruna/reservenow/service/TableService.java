package org.vedruna.reservenow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.vedruna.reservenow.persistance.model.Reservation;
import org.vedruna.reservenow.persistance.model.Restaurant;
import org.vedruna.reservenow.persistance.model.TableEntity;
import org.vedruna.reservenow.persistance.repository.ReservationRepository;
import org.vedruna.reservenow.persistance.repository.TableRepositoryI;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepositoryI tableRepository;
    private final ReservationRepository reservationRepository;

    /**
     * Verifica si una mesa está disponible en una fecha y hora específicas.
     */

    public boolean isTableAvailable(String restaurantName, Integer tableNumber, LocalDateTime reservationDateTime) {
    LocalDate requestedDate = reservationDateTime.toLocalDate();

    List<Reservation> existingReservations = reservationRepository
        .findByRestaurantNameAndTableNumber(restaurantName, tableNumber);

    return existingReservations.stream()
        .filter(res -> res.getStatus() == Reservation.Status.CONFIRMADA)
        .map(res -> res.getReservationDate().toLocalDate())
        .noneMatch(date -> date.equals(requestedDate));
}



    /**
     * Busca una mesa disponible para un restaurante, fecha y número de invitados.
     */
    public TableEntity findAvailableTable(Restaurant restaurant, LocalDateTime reservationDate, int numGuests) {
        // Busca todas las mesas del restaurante que tengan suficiente capacidad
        List<TableEntity> candidateTables = tableRepository.findByRestaurantAndCapacityGreaterThanEqual(restaurant, numGuests);

        for (TableEntity table : candidateTables) {
            boolean available = isTableAvailable(restaurant.getRestaurantName(), table.getTableNumber(), reservationDate);
            if (available) {
                return table;
            }
        }

        // No se encontró ninguna mesa disponible
        return null;
    }
}
