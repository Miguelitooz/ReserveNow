package org.vedruna.reservenow.dto;

import org.vedruna.reservenow.persistance.model.Rol;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RolDTO {
    Integer rolId;
    String rolName;

    public RolDTO(Rol rol) {
        this.rolId = rol.getRolId();
        this.rolName = rol.getRolName();
    }
}

