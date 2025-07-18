package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.user.PasswordResetToken;
import com.maosencantadas.model.domain.user.User;

public interface PasswordResetService {
    PasswordResetToken createToken(String email);

    User validateToken(String token);

    void invalidateToken(String token);
}
