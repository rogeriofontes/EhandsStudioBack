package com.maosencantadas.model.domain.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginResponseDTO", description = "DTO returned after successful login")
public record LoginResponseDTO(String token) {

}

