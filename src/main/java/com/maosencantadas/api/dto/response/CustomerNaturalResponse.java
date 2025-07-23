package com.maosencantadas.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Valid
@Schema(name = "ArtistDTO", description = "DTO representing an artist")
public class CustomerNaturalResponse {

    @NotBlank(message = "CPF is mandatory")
    @Schema(description = "Artist's CPF", example = "123.456.789-00")
    private String cpf;

    @NotBlank(message = "CPF is mandatory")
    @Schema(description = "Artist's CPF", example = "123.456.789-00")
    private String documentNumber;

    @NotBlank(message = "RG is mandatory")
    @Schema(description = "Artist's RG", example = "12.345.678-9")
    private String identityNumber;

    @NotNull(message = "birthDate is mandatory")
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
