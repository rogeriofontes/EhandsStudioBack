package com.maosencantadas.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "PersonNaturalRequest", description = "DTO representing a category of products")
public class PersonNaturalRequest implements Serializable {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    private String name;

    @Email(message = "Invalid email")
    @Schema(description = "Artist email", example = "testing@example.com")
    private String email;

    @Size(max = 20, message = "Phone number must have a maximum of 20 characters")
    @Schema(description = "Artist's phone number", example = "(34) 98765-4321")
    private String phone;

    @Schema(description = "Artist's WhatsApp number", example = "(11) 91234-5678")
    private String whatsapp;

    @Size(max = 200, message = "Address must have a maximum of 200 characters")
    @Schema(description = "Artist address", example = "Rua Padre Pio, 123 - Centro")
    private String address;

    @NotBlank(message = "CPF is mandatory")
    @Schema(description = "Artist's CPF", example = "123.456.789-00")
    private String documentNumber;

    @NotBlank(message = "RG is mandatory")
    @Schema(description = "Artist's RG", example = "12.345.678-9")
    private String identityNumber;

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Artist's birth date", example = "1990-01-01")
    private LocalDate birthDate;
}
