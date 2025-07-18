package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.*;
import com.maosencantadas.api.mapper.UserMapper;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.service.AuthenticationService;
import com.maosencantadas.utils.RestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@CrossOrigin(origins = "*")//, allowedHeaders = "*", allowCredentials = "true")
public class AuthController {

    private final AuthenticationService service;

    private final UserMapper userMapper;

    @Operation(summary = "User Login",
            description = "Authenticate a user and return a JWT token if successful.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, token returned"),
                    @ApiResponse(responseCode = "401", description = "Invalid login credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        AuthResponse register = service.authenticate(request);
        log.info("User logged in successfully: {}", request.email());
        if (register == null) {
            log.warn("Login failed for user: {}", request.email());
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok(register);
    }

    @Operation(summary = "User Registration",
            description = "Register a new user by providing login, password, and role.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "User with given login already exists"),
                    @ApiResponse(responseCode = "500", description = "Server error while saving user")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserDTO userDTO) {
        log.info("Registering user: {}", userDTO);
        User entity = userMapper.toEntity(userDTO);
        User register = service.register(entity);
        UserDTO registeredUser = userMapper.toDTO(register);

        log.info("User registered Association {] id: {} - email: {} - Perfil: {}", registeredUser.getId(), registeredUser.getEmail(), registeredUser.getUserRole());
        URI location = RestUtils.getUri(registeredUser.getId());
        return ResponseEntity.created(location).body(registeredUser);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestRefreshToken = request.get("refreshToken");
        ResponseEntity<Map<String, String>> mapResponseEntity = service.refreshToken(requestRefreshToken);
        if (mapResponseEntity.getStatusCode().isError()) {
            log.error("Failed to refresh token for request: {}", request);
            return ResponseEntity.status(mapResponseEntity.getStatusCode()).body(mapResponseEntity.getBody());
        }
        log.info("Token refreshed successfully for request: {}", request);
        return ResponseEntity.ok(mapResponseEntity.getBody());
    }

    @Operation(summary = "Find user by ID",
            description = "Returns a specific user by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @GetMapping("/activation-token")
    public ResponseEntity<Boolean> generateActivationToken(@RequestParam("token") String token) {
        boolean validated = service.validateActiveToken(token);
        if (!validated) {
            log.warn("Invalid activation token: {}", token);
            return ResponseEntity.status(400).body(false);
        }

        log.info("Activation token validated successfully: {}", token);
        return ResponseEntity.ok(true);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    @Operation(summary = "Find category by ID", description = "Returns a specific category by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductCategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserDTO>> findUsers() {
        List<User> all = service.findAll();
        List<UserDTO> list = userMapper.toDTO(all);
        return ResponseEntity.ok(list);
    }

    @Operation(summary = "Update a ResidentDetails by Id", tags = {"associateDetailss", "put"})
    @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ForgotPasswordResponse.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    @PutMapping("/forgot-password")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        User user = service.forgotPassword(email);
        if (user == null) {
            log.warn("User not found for email: {}", email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado com o e-mail fornecido.");
        }

        return ResponseEntity.ok("E-mail enviado!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        User user = service.validatePasswordToken(token, newPassword);
        if (user == null) {
            log.warn("Invalid token or user not found for token: {}", token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou usuário não encontrado.");
        }

        return ResponseEntity.ok("Senha redefinida com sucesso!");
    }
}
