package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.AuthRequest;
import com.maosencantadas.api.dto.AuthResponse;
import com.maosencantadas.model.domain.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface AuthenticationService {
    User register(User user);

    AuthResponse authenticate(AuthRequest request);

    List<User> findAll();

    boolean validateActiveToken(String token);

    ResponseEntity<Map<String, String>> refreshToken(String requestRefreshToken);

    User forgotPassword(String email);

    User validatePasswordToken(String token, String newPassword);
}
