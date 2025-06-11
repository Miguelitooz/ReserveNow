package org.vedruna.reservenow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.vedruna.reservenow.dto.ReservationDTO;
import org.vedruna.reservenow.dto.UserDTO;
import org.vedruna.reservenow.persistance.model.Reservation;
import org.vedruna.reservenow.persistance.model.User;
import org.vedruna.reservenow.persistance.repository.UserRepositoryI;

@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserRepositoryI userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO selectMyUser(User user) {
        return new UserDTO(user);
    }

    //Metodo Registro
    @Override
    public UserDTO registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }
    
    //Metodo borrar usuario por su ID
    @Override
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    //Metodo obtener tu perfil propio
    @Override
    public UserDTO getCurrentUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        return new UserDTO(user);
    }

    private ReservationDTO convertToDTO(Reservation reservation) {
    return new ReservationDTO(
        reservation.getReservationId(),
        reservation.getUser().getUsername(),
        reservation.getRestaurant().getRestaurantName(),
        reservation.getTable().getTableNumber(),
        reservation.getReservationDate(),
        reservation.getNumGuests(),
        reservation.getFoodAllergies(),
        reservation.getStatus()
    );
}

}
