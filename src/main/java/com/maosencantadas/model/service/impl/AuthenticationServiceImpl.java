package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.AuthRequest;
import com.maosencantadas.api.dto.AuthResponse;
import com.maosencantadas.commons.Constants;
import com.maosencantadas.exception.RegisterUserException;
import com.maosencantadas.infra.security.TokenService;
import com.maosencantadas.model.domain.user.ActivationToken;
import com.maosencantadas.model.domain.user.PasswordResetToken;
import com.maosencantadas.model.domain.user.RefreshToken;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.ActivationTokenRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.AuthenticationService;
import com.maosencantadas.model.service.EmailService;
import com.maosencantadas.model.service.PasswordResetService;
import com.maosencantadas.model.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    public static final String RECUPERACAO_DE_SENHA = "Recuperação de Senha";
    private final AuthenticationManager authenticationManager;
    private final PasswordResetService passwordResetService;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final ActivationTokenRepository activationTokenRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final FF4j ff4j;

    @Override
    public User register(User user) {
        // Verifica se o usuário já existe pelo email
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            log.warn("User with email {} already exists", user.getEmail());
            throw new RegisterUserException("Usuário já cadastrado com o email: " + user.getEmail());
        }

        // Criptografa a senha do usuário
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 1. Salva o usuário primeiro
        var userSaved = userRepository.save(user);
        log.info("User registered successfully: {}", userSaved.getName());

        String validatedToken = getValidatedToken(userSaved);
        log.info("User registered successfully: {}", userSaved.getName());

        if (ff4j.check(Constants.SEND_EMAIL)) {
            sendEmail(user.getName(), user.getEmail(), validatedToken);
        }

        log.info("Email sent to user: {}", userSaved.getEmail());
        return userSaved;
    }


    @Override
    public AuthResponse authenticate(AuthRequest request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        log.info("Authentication successful for user: {}", auth.getPrincipal());

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RegisterUserException("Usuário não encontrado com o email: " + request.email()));

        var token = tokenService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());

        log.info("User authenticated successfully: {}", request.email());
        return new AuthResponse(token, refreshToken.getToken());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean validateActiveToken(String token) {
        ActivationToken byToken = activationTokenRepository.findByToken(token);
        if (byToken == null || byToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            log.warn("Token is invalid or expired: {}", token);
            return false;
        }

        log.info("Token is valid: {}", token);
        try {
            if (!byToken.isActive()) {
                log.warn("Token is already active: {}", token);
                User user = byToken.getUser();
                activationTokenRepository.activateTokenByUserId(user.getId());
            } else {
                log.info("Token is already active: {}", token);
            }
            return true;
        } catch (Exception e) {
            log.error("Error checking token expiry: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> refreshToken(String requestRefreshToken) {
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = tokenService.generateToken(user);
                    return ResponseEntity.ok(Map.of("accessToken", token));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token not found!"));
    }

    @Override
    public User forgotPassword(String email) {
        PasswordResetToken token = passwordResetService.createToken(email);

        if (ff4j.check(Constants.SEND_EMAIL)) {
            sendEmailPasswordRecovery(token.getUser().getName(), token.getUser().getEmail(), token.getToken());
        }

        log.info("Password reset successfully for user: {}", token.getUser().getEmail());
        return token.getUser();
    }

    @Override
    @Transactional
    public User validatePasswordToken(String token, String newPassword) {
        User user = passwordResetService.validateToken(token);
        if (user == null) {
            log.warn("Invalid password reset token: {}", token);
            throw new RegisterUserException("Token de recuperação de senha inválido ou expirado.");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        User savedUser = userRepository.save(user);
        passwordResetService.invalidateToken(token);

        log.info("Password reset successfully for User: {}", savedUser.getEmail());
        return savedUser;
    }

    private void sendEmail(String name, String email, String validatedToken) {
        try {
            emailService.publish(name, email, "Bem-vindo ao Mãos encantadas!", true, validatedToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getValidatedToken(User user) {
        ActivationToken activationToken = ActivationToken.builder()
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(1)) // Token válido por 1 dia
                .token(tokenService.generateActivationToken())
                .active(false)
                .build();

        ActivationToken saved = activationTokenRepository.save(activationToken);
        log.info("Activation token generated): {}", saved.getToken());
        return saved.getToken();
    }

    private void sendEmailPasswordRecovery(String name, String email, String token) {
        try {
            emailService.publishPasswordRecovery(name, email, RECUPERACAO_DE_SENHA, token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
