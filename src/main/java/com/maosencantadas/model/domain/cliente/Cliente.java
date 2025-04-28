package com.maosencantadas.model.domain.cliente;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Schema(name = "Cliente", description = "Representa um cliente")
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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
    @Schema(description = "Núme
    
}
