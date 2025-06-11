package org.vedruna.reservenow.security.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vedruna.reservenow.dto.ResponseDTO;
import org.vedruna.reservenow.security.auth.dto.AuthResponseDTO;
import org.vedruna.reservenow.security.auth.dto.LoginRequestDTO;
import org.vedruna.reservenow.security.auth.dto.RegisterRequestDTO;
import org.vedruna.reservenow.security.auth.service.AuthServiceI;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthServiceI authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        authService.register(request);
        return ResponseEntity.ok(new ResponseDTO("User registered successfully"));
    }

}
