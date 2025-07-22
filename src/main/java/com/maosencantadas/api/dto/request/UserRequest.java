package com.maosencantadas.api.dto.request;

import com.maosencantadas.commons.StrongPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "UserRequest", description = "DTO representing a category of products")
public class UserRequest implements Serializable {

    @Schema(description = "User login", example = "thomas123")
    private String name;

    @Schema(description = "User email", example = "")
    private String email;

    @Schema(description = "User password", example = "password123")
    @StrongPassword(message = "Senha fraca: deve conter ao menos 8 caracteres, incluindo maiúsculas, minúsculas, número e símbolo.")
    private String password;

    @Schema(description = "User role", example = "CUSTOMER")
    private String role;
}
