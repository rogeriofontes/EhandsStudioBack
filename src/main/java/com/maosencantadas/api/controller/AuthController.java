package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.AuthRequest;
import com.maosencantadas.api.dto.AuthResponse;
import com.maosencantadas.api.dto.CategoryDTO;
import com.maosencantadas.api.dto.UserDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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
        log.info("User logged in successfully: {}", request.login());
        if (register == null) {
            log.warn("Login failed for user: {}", request.login());
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
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserDTO>> findUsers() {
        List<User> all = service.findAll();
        List<UserDTO> list = userMapper.toDTO(all);
        return ResponseEntity.ok(list);
    }
}
