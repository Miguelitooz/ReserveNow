package org.vedruna.reservenow.dto;

import org.vedruna.reservenow.persistance.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String username;
    private String email;
    private String rolName;

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.rolName = user.getUserRol().getRolName();
     
    }


}
