package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "UserDTO", description = "DTO representing a user")
public class UserDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "User login", example = "thomas123")
    private String login;

    @Schema(description = "User role", example = "CUSTOMER")
    private String userRole;
}

