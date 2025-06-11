package org.vedruna.reservenow.security.auth.service;

import org.vedruna.reservenow.security.auth.dto.AuthResponseDTO;
import org.vedruna.reservenow.security.auth.dto.LoginRequestDTO;
import org.vedruna.reservenow.security.auth.dto.RegisterRequestDTO;

public interface AuthServiceI {
    AuthResponseDTO login(LoginRequestDTO request);
    void register(RegisterRequestDTO request);
}
