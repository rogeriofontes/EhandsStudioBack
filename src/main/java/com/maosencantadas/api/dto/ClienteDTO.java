package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(name = "ClienteDTO", description = "DTO que representa um cliente")
public class ClienteDTO {

    @Schema(description = "Identifica o cliente", example = "1")
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do cliente", example = "Rogério Testando Cliente")
    private String nome;

    @Size(max = 200, message = "Endereço deve ter no máximo 200 caracteres")
    @Schema(description = "Endereço do cliente", example = "Avenida Teste Cliente 456 - Centro")
    private String endereco;

    @Email(message = "Email inválido")
    @Schema(description = "Endereço de email do cliente", example = "testandoCleinte@example.com")
    private String email;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Schema(description = "Número de telefone do cliente", example = "(21) 99876-5432")
    private String telefone;
}
