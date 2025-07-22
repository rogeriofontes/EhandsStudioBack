package com.maosencantadas.api.dto.request;

import com.maosencantadas.model.domain.person.StateRegistration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "PersonLegalRequest", description = "DTO representing a category of products")
public class PersonLegalRequest implements Serializable {

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

    @Schema(description = "CNPJ of the legal entity", example = "12.345.678/0001-90")
    @EqualsAndHashCode.Include
    private String documentNumber; // CNPJ

    @EqualsAndHashCode.Include
    private String companyName; // razão social

    @EqualsAndHashCode.Include
    private String fantasyName; // nome fantasia

    @Enumerated(EnumType.STRING)
    private StateRegistration stateRegistration; // pode me ajudar a criar um enum para state registration?

    private String municipalRegistration;

    private String legalRepresentative;

    private String legalRepresentativePhone; // Telefone do representante legal
}
