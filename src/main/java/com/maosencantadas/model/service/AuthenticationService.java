package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.AuthRequest;
import com.maosencantadas.api.dto.AuthResponse;
import com.maosencantadas.model.domain.user.User;

import java.util.List;

public interface AuthenticationService {
    User register(User user);

    //RegisterResponse forgotPassword(ForgotPasswordRequest request) throws Exception;

    //RegisterResponse resetPassword(ResetPasswordRequest request) throws Exception;

    AuthResponse authenticate(AuthRequest request);

    List<User> findAll();

    // RegisterResponse getUser(Long id);

   // RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}
