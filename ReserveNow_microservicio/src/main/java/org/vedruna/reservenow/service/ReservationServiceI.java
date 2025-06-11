package org.vedruna.reservenow.service;
 
import org.vedruna.reservenow.dto.ReservationDTO;
import org.vedruna.reservenow.persistance.model.Reservation;
import java.util.List;

public interface ReservationServiceI {
    List<ReservationDTO> getUserReservations(Integer userId);

ReservationDTO createReservation(ReservationDTO dto);

    ReservationDTO updateReservation(ReservationDTO dto);

    void cancelReservation(Integer id, String username);
}
