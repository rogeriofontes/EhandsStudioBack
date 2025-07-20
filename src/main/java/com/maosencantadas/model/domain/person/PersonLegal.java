package com.maosencantadas.model.domain.person;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_person_legal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersonLegal extends Person {

    @Column(unique = true, nullable = false)
    private String cnpj;
    private String companyName; // razão social
    private String stateRegistration;
}
