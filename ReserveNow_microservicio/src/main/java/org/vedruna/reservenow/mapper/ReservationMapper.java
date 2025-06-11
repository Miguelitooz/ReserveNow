package org.vedruna.reservenow.mapper;

import org.vedruna.reservenow.persistance.model.User;
import org.springframework.stereotype.Component;
import org.vedruna.reservenow.dto.ReservationDTO;
import org.vedruna.reservenow.persistance.model.Reservation;
import org.vedruna.reservenow.persistance.model.Restaurant;
import org.vedruna.reservenow.persistance.model.TableEntity;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationDTO dto) {
        Reservation entity = new Reservation();

        entity.setReservationId(dto.getReservationId());

        User user = new User();
        user.setUsername(dto.getUsername());  // cuidado: esto no es la PK, no sirve para persistir

        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(dto.getRestaurantName());  // corregido

        TableEntity table = new TableEntity();
        table.setTableNumber(dto.getTableNumber());

        entity.setUser(user);
        entity.setRestaurant(restaurant);
        entity.setTable(table);

        entity.setReservationDate(dto.getReservationDate());
        entity.setNumGuests(dto.getNumGuests());
        entity.setFoodAllergies(dto.getFoodAllergies());
        entity.setStatus(dto.getStatus());

        return entity;
    }
    public ReservationDTO toDTO(Reservation reservation) {
        return new ReservationDTO(
            reservation.getReservationId(),
            reservation.getUser().getUsername(),
            reservation.getRestaurant().getRestaurantName(),
            reservation.getTable() != null ? reservation.getTable().getTableNumber() : null,
            reservation.getReservationDate(),
            reservation.getNumGuests(),
            reservation.getFoodAllergies(),
            reservation.getStatus()
        );
    }

}