package org.vedruna.reservenow.controller.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.vedruna.reservenow.dto.ReservationDTO;
import org.vedruna.reservenow.persistance.model.User;
import org.vedruna.reservenow.service.ReservationServiceI;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private ReservationServiceI reservationService;

    // Obtener reservas del usuario autenticado
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getUserReservations(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        List<ReservationDTO> reservations = reservationService.getUserReservations(user.getUserId());
        return ResponseEntity.ok(reservations);
    }
    
    // Crear reserva nueva
    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        reservationDTO.setUsername(username);

        // Llamamos a createReservation que devuelve ReservationDTO
        ReservationDTO saved = reservationService.createReservation(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Actualizar reserva existente
    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable Integer id,
            @RequestBody ReservationDTO reservationDTO) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        reservationDTO.setUsername(username);
        reservationDTO.setReservationId(id);

        return ResponseEntity.ok(reservationService.updateReservation(reservationDTO));
    }

    // Cancelar reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        reservationService.cancelReservation(id, username);

        return ResponseEntity.noContent().build();
    }
}
