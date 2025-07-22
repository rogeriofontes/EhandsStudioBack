package com.maosencantadas.api.dto.response;

import com.maosencantadas.model.domain.person.StateRegistration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Valid
@Schema(name = "ArtistDTO", description = "DTO representing an artist")
public class ArtistLegalResponse {

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

    private String legalRepresentativePhone; // Telefone do repre
}
