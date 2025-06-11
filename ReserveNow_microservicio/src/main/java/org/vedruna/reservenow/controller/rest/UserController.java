package org.vedruna.reservenow.controller.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.vedruna.reservenow.dto.UserDTO;
import org.vedruna.reservenow.persistance.model.Reservation;
import org.vedruna.reservenow.persistance.model.User;
import org.vedruna.reservenow.service.ReservationServiceI;
import org.vedruna.reservenow.service.UserServiceI;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserServiceI userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    
        // Obtener el nombre de usuario con el token devuelto
        String username = principal.getName();
    
        // Obtener el perfil completo del usuario autenticado
        UserDTO user = userService.getCurrentUserProfile(username);
    
        return ResponseEntity.ok(user);
    }


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO registeredUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @DeleteMapping("/perfil/eliminar")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal User user) {
        userService.deleteUser(user.getUserId());
        return ResponseEntity.ok("Cuenta eliminada correctamente");
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    
}