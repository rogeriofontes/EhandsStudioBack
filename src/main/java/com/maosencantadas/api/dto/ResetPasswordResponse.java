package com.maosencantadas.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.management.relation.Role;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
@Schema(description = "Driver Request")
public class ResetPasswordResponse implements Serializable {
    private Long id;
    private String name;
    private String email;
    @JsonIgnore
    private String password;
    private Role role;
    private Long userId;
}
