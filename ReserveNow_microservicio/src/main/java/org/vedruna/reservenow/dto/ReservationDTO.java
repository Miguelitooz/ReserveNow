package org.vedruna.reservenow.dto;

import java.time.LocalDateTime;
import org.vedruna.reservenow.persistance.model.Reservation.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ReservationDTO {

    private Integer reservationId;
    private String username;        // para mostrar el nombre de usuario
    private String restaurantName;  // para mostrar nombre del restaurante
    private Integer tableNumber;    // para mostrar número de mesa
    private LocalDateTime reservationDate;
    private int numGuests;
    private String foodAllergies;
    private Status status;

    public ReservationDTO(Integer reservationId, String username, String restaurantName, Integer tableNumber,
        LocalDateTime reservationDate, int numGuests, String foodAllergies, Status status) {
        this.reservationId = reservationId;
        this.username = username;
        this.restaurantName = restaurantName;
        this.tableNumber = tableNumber;
        this.reservationDate = reservationDate;
        this.numGuests = numGuests;
        this.foodAllergies = foodAllergies;
        this.status = status;
    }
}
