package org.vedruna.reservenow.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vedruna.reservenow.dto.UserDTO;
import org.vedruna.reservenow.persistance.model.User;

public interface UserServiceI {
    UserDTO selectMyUser(User user);
    UserDTO registerUser(User user);
    void deleteUser(Integer userId);
    UserDTO getCurrentUserProfile(String username);
}
