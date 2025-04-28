package com.maosencantadas.model.domain.orcamento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.maosencantadas.model.domain.cliente.Cliente;
import com.maosencantadas.model.domain.produto.Produto;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Table(name = "Orcamentos")
@Schema(name = "Orcamento", description = "Representa um orçamento")
public class Orcamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identifica do orçamento", example = "1")
    private Long id;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 50, message = "Status deve ter no máximo 50 caracteres")
    @Schema(description = "Status atual do orçamento", example = "Pendente")
    private String status;

    @NotNull(message = "Data do orçamento é obrigatória")
    @Schema(description = "Data e hora em que o orçamento foi criado", example = "2024-04-27T15:30:00")
    private LocalDateTime dataOrcamento;

    @Schema(description = "URL da imagem associada ao orçamento", example = "https://exampleorcamento.com/imagem.png")
    private String imagemUrl;

    @NotNull(message = "ClienteId é obrigatório")
    @Schema(description = "Identificador do cliente associado ao orçamento", example = "5")
    private Long clienteId;

    @NotNull(message = "ProdutoId é obrigatório")
    @Schema(description = "Identificador do produto associado ao orçamento", example = "3")
    private Long produtoId;

    
}
