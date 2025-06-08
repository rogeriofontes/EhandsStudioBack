package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(name = "CustomerDTO", description = "DTO representing a customer")
public class CustomerDTO {

    @Schema(description = "Identifies the customer", example = "1")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Schema(description = "Customer name", example = "Thomas")
    private String name;

    @Size(max = 200, message = "Address must have a maximum of 200 characters")
    @Schema(description = "Customer address", example = "Avenida Teste Cliente 456 - Centro")
    private String address;

    @Email(message = "Invalid email")
    @Schema(description = "Customer email address", example = "testingcustomer@example.com")
    private String email;

    @Schema(description = "Customer phone number", example = "(21) 99876-5432")
    private String phone;
}
