package com.maosencantadas.model.domain.person;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_person_natural")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersonNatural extends Person {

    @NotBlank(message = "CPF is mandatory")
    @Column(name="document_number", unique = true, nullable = false)
    @Schema(description = "Artist's CPF", example = "123.456.789-00")
    private String documentNumber;

    @NotBlank(message = "RG is mandatory")
    @Column(name="identify_number", unique = true, nullable = false)
    @Schema(description = "Artist's RG", example = "12.345.678-9")
    private String identityNumber;

    @NotNull(message = "birthDate is mandatory")
    @Schema(description = "Artist's birth date", example = "1990-01-01")
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
