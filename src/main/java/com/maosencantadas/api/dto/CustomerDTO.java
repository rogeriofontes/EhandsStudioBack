package com.maosencantadas.api.dto;

import com.maosencantadas.model.domain.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(name = "CustomerDTO", description = "DTO representing a customer")
public class CustomerDTO {

    @Schema(description = "Identifies the customer", example = "1")
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name must have a maximum of 100 characters")
    @Schema(description = "Customer name", example = "Thomas")
    private String name;

    @Size(max = 200, message = "Address must have a maximum of 200 characters")
    @Schema(description = "Customer address", example = "Avenida Teste Cliente 456 - Centro")
    private String address;

    @Email(message = "Invalid email")
    @Schema(description = "Customer email address", example = "testingcustomer@example.com")
    private String email;

    @NotBlank(message = "CPF is mandatory")
    @Schema(description = "Customer's CPF", example = "123.456.789-00")
    private String cpf;

    @Schema(description = "Customer phone number", example = "(21) 99876-5432")
    private String phone;

    @Schema(description = "Customer's WhatsApp number", example = "(11) 91234-5678")
    private String whatsapp;

    @Schema(description = "User ID associated with the customer", example = "id: 2, login: test@test.com, password: ******, UserRole: CUSTOMER")
    private UserDTO user;


}
