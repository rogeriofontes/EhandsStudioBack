package com.maosencantadas.api.dto;

import com.maosencantadas.commons.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "UserDTO", description = "DTO representing a user")
public class UserDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User login", example = "thomas123")
    private String name;

    @Schema(description = "User email", example = "")
    private String email;

    @Schema(description = "User password", example = "password123")
    @StrongPassword(message = "Senha fraca: deve conter ao menos 8 caracteres, incluindo maiúsculas, minúsculas, número e símbolo.")
    private String password;

    @Schema(description = "User role", example = "CUSTOMER")
    private String userRole;
}

