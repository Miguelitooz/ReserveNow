package org.vedruna.reservenow.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vedruna.reservenow.dto.ReservationDTO;
import org.vedruna.reservenow.exception.ReservationException;
import org.vedruna.reservenow.mapper.ReservationMapper;
import org.vedruna.reservenow.persistance.model.Reservation;
import org.vedruna.reservenow.persistance.model.Restaurant;
import org.vedruna.reservenow.persistance.model.TableEntity;
import org.vedruna.reservenow.persistance.model.User;
import org.vedruna.reservenow.persistance.repository.ReservationRepository;
import org.vedruna.reservenow.persistance.repository.RestaurantRepositoryI;
import org.vedruna.reservenow.persistance.repository.TableRepositoryI;
import org.vedruna.reservenow.persistance.repository.UserRepositoryI;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationServiceI {

    private final ReservationRepository reservationRepository;
    private final UserRepositoryI userRepository;
    private final RestaurantRepositoryI restaurantRepository;
    private final TableRepositoryI tableRepository;
    private final ReservationMapper reservationMapper;
    private final TableService tableService;

    @Override
    public List<ReservationDTO> getUserReservations(Integer userId) {
        List<ReservationDTO> allReservations = reservationRepository.findReservationsByUserId(userId);
        return allReservations.stream()
                .filter(r -> r.getStatus() == Reservation.Status.CONFIRMADA)
                .toList();
    }

    @Override
    public ReservationDTO createReservation(ReservationDTO dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<Reservation> activeReservations = reservationRepository.findByUserAndStatus(user, Reservation.Status.CONFIRMADA);
        if (!activeReservations.isEmpty()) {
            throw new ReservationException("Ya tienes una reserva activa y no puedes hacer otra.");
        }

        Restaurant restaurant = restaurantRepository.findByRestaurantName(dto.getRestaurantName())
                .orElseThrow(() -> new ReservationException("Restaurante no encontrado"));

        TableEntity assignedTable = tableService.findAvailableTable(restaurant, dto.getReservationDate(), dto.getNumGuests());
        if (assignedTable == null) {
            throw new ReservationException("No hay mesas disponibles para la fecha indicada.");
        }

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRestaurant(restaurant);
        reservation.setTable(assignedTable);
        reservation.setReservationDate(dto.getReservationDate());
        reservation.setNumGuests(dto.getNumGuests());
        reservation.setFoodAllergies(dto.getFoodAllergies());
        reservation.setStatus(Reservation.Status.CONFIRMADA);

        Reservation saved = reservationRepository.save(reservation);
        return reservationMapper.toDTO(saved);
    }

    @Override
    public ReservationDTO updateReservation(ReservationDTO dto) {
        Reservation existing = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new ReservationException("Reserva no encontrada"));

        if (!existing.getUser().getUsername().equals(dto.getUsername())) {
            throw new ReservationException("No estás autorizado para editar esta reserva.");
        }

        existing.setReservationDate(dto.getReservationDate());
        existing.setNumGuests(dto.getNumGuests());
        existing.setFoodAllergies(dto.getFoodAllergies());

        Reservation updated = reservationRepository.save(existing);
        return reservationMapper.toDTO(updated);
    }

    @Override
    public void cancelReservation(Integer id, String username) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException("Reserva no encontrada"));

        if (!reservation.getUser().getUsername().equals(username)) {
            throw new ReservationException("No estás autorizado para cancelar esta reserva.");
        }

        reservation.setStatus(Reservation.Status.CANCELADA);
        reservationRepository.save(reservation);
    }
}
