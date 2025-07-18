package com.maosencantadas.model.service.impl;

import com.maosencantadas.model.domain.user.RefreshToken;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.RefreshTokenRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh.expiration}") // por ex: "7d"
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<User> byId = userRepository.findById(userId);

        if (byId.isEmpty()) {
            log.error("User not found with id: {}", userId);
            throw new RuntimeException("User not found");
        }
        log.info("Creating refresh token for user: {}", byId.get().getUsername());

        refreshToken.setUser(byId.get());
        refreshToken.setExpiryDate(LocalDateTime.now().plusDays(7));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        log.info("Refresh token created: {}", refreshToken.getToken());

        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired. Please login again");
        }
        return token;
    }
}
