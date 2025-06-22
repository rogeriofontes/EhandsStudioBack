package com.maosencantadas.model.domain.customer;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.maosencantadas.model.domain.AuditDomain;
import com.maosencantadas.model.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "tb_customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Builder
@Schema(name = "Customer", description = "Represents a customer")
public class Customer extends AuditDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identifies the customer", example = "1")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Column(name = "name")
    @Schema(description = "Customer name", example = "Thomas")
    private String name;

    @Size(max = 200, message = "Address must have a maximum of 200 characters")
    @Column(name = "address")
    @Schema(description = "Customer address", example = "Avenida Teste Cliente 456 - Centro")
    private String address;

    @Email(message = "Invalid email")
    @Column(name = "email")
    @Schema(description = "Customer email address", example = "testingcustomer@example.com")
    private String email;

    @Column(name = "cpf")
    @Schema(description = "Customer cpf number", example = "111.111.111-11")
    private String cpf;

    @Column(name = "phone")
    @Schema(description = "Customer phone number", example = "(21) 99876-5432")
    private String phone;

    @Column(name = "whatsapp")
    @Schema(description = "Customer whatsapp phone number", example = "(21) 99876-5432")
    private String whatsapp;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    @Schema(description = "Customer user")
    private User user;

    
}
