package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.AuthRequest;
import com.maosencantadas.api.dto.AuthResponse;
import com.maosencantadas.commons.Constants;
import com.maosencantadas.exception.RegisterUserException;
import com.maosencantadas.infra.security.TokenService;
import com.maosencantadas.model.domain.user.ActivationToken;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.repository.ActivationTokenRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.AuthenticationService;
import com.maosencantadas.model.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ff4j.FF4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final ActivationTokenRepository activationTokenRepository;
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
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        log.info("User authenticated successfully: {}", request.login());
        if (ff4j.check(Constants.SEND_EMAIL)) {
            // sendEmail(request.login(), ((User) auth.getPrincipal()).getEmail());
            log.info("Email sent to user authenticate: {}", ((User) auth.getPrincipal()).getEmail());
        }

        return new AuthResponse(token);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    private void sendEmail(String name, String email, String validatedToken) {
        try {
            emailService.publish(name, email, "Bem-vindo ao MentorAPI", true, validatedToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getValidatedToken(User user) {
        ActivationToken activationToken = ActivationToken.builder()
                .user(user)
                .expiryDate(LocalDateTime.now().plusDays(1)) // Token válido por 1 dia
                .token(tokenService.generateActivationToken())
                .build();

        ActivationToken saved = activationTokenRepository.save(activationToken);
        log.info("Activation token generated): {}", saved.getToken());
        return saved.getToken();
    }
}
