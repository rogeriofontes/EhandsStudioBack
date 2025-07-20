package com.maosencantadas.model.domain.person;

import jakarta.persistence.*;
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
public class PersonNatural {

    @Column(unique = true, nullable = false)
    private String cpf;
    private String rg;
    private LocalDate birthDate;
}
