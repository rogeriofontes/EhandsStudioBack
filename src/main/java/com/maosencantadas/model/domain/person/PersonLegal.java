package com.maosencantadas.model.domain.person;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_person_legal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersonLegal extends Person {

    @Schema(description = "CNPJ of the legal entity", example = "12.345.678/0001-90")
    @Column(name = "document_number", unique = true, nullable = false)
    @EqualsAndHashCode.Include
    private String documentNumber; // CNPJ

    @Column(name = "company_name", nullable = false)
    @EqualsAndHashCode.Include
    private String companyName; // razão social

    @Column(name = "fantasy_name")
    @EqualsAndHashCode.Include
    private String fantasyName; // nome fantasia

    @Column(name = "state_registration")
    @Enumerated(EnumType.STRING)
    private StateRegistration stateRegistration; // pode me ajudar a criar um enum para state registration?

    @Column(name = "municipal_registration")
    private String municipalRegistration;

    @Column(name = "legal_representative")
    private String legalRepresentative;

    @Column(name = "legal_representative_phone")
    private String legalRepresentativePhone; // Telefone do representante legal
}
