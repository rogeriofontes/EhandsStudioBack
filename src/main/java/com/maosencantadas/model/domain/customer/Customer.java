package com.maosencantadas.model.domain.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Schema(name = "Customer", description = "Represents a customer")

public class Customer {

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

    @Column(name = "phone")
    @Schema(description = "Customer phone number", example = "(21) 99876-5432")
    private String phone;
    
}
