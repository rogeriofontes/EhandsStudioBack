package com.maosencantadas.model.service.impl;

import com.maosencantadas.model.domain.user.PasswordResetToken;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.PasswordResetTokenRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public PasswordResetToken createToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        tokenRepository.deleteByUser(user); // Remove tokens antigos

        PasswordResetToken token = new PasswordResetToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusMinutes(30)); // Token 30 min válido
        return tokenRepository.save(token);
    }

    @Override
    public User validateToken(String token) {
        PasswordResetToken prt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (prt.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }
        return prt.getUser();
    }

    @Override
    public void invalidateToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }
}
