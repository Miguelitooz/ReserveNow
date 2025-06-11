package org.vedruna.reservenow.service;

import org.vedruna.reservenow.dto.RolDTO;

public interface RolServiceI {
    RolDTO selectRolByName(String rolName);
}
