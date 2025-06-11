package org.vedruna.reservenow.security.auth.service;

import java.security.Key;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.vedruna.reservenow.persistance.model.User;


public interface JWTServiceI {
    String getToken(User user);

    String getToken(Map<String, Object> extraClaims, User user);

    Key getKey();

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
