package com.maosencantadas.model.domain.person;

import com.maosencantadas.model.domain.AuditDomain;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
@EqualsAndHashCode(callSuper = false)
public abstract class Person extends AuditDomain {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identify the artist", example = "1")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Schema(description = "Artist name", example = "Amanda")
    private String name;

    @Email(message = "Invalid email")
    @Column(name="email")
    @Schema(description = "Artist email", example = "testing@example.com")
    private String email;

    @Size(max = 20, message = "Phone number must have a maximum of 20 characters")
    @Column(name="phone")
    @Schema(description = "Artist's phone number", example = "(34) 98765-4321")
    private String phone;

    @Schema(description = "Artist's WhatsApp number", example = "(11) 91234-5678")
    @Column(name="whatsapp")
    private String whatsapp;

    @Size(max = 200, message = "Address must have a maximum of 200 characters")
    @Column(name="address")
    @Schema(description = "Artist address", example = "Rua Padre Pio, 123 - Centro")
    private String address;
}
