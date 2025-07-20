package com.maosencantadas.model.domain.person;

import com.maosencantadas.model.domain.AuditDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public abstract class Person extends AuditDomain {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
}
