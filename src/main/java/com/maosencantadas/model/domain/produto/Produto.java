package com.maosencantadas.model.domain.produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.math.BigDecimal;

import com.maosencantadas.model.domain.artista.Artista;
import com.maosencantadas.model.domain.categoria.Categoria;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "produtos")
@Schema(name = "Produto", description = "Representa um produto")
public class Produto {
		
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "Identificador do produto", example = "1")
    private Long id;

    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    @Schema(description = "Nome do produto", example = "Escultura de Cavalo")
    private String nome;

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    @Schema(description = "Descrição do produto", example = "Escultura artesanal de um cavalo, feita com materiais recicláveis.")
    private String descricao;

    @Size(max = 50, message = "Tamanho deve ter no máximo 50 caracteres")
    @Schema(description = "Tamanho do produto", example = "Médio")
    private String tamanho;

    @Schema(description = "URL da imagem do produto", example = "https://example.com/produto-imagem.jpg")
    private String imagemUrl;

    @DecimalMin(value = "0.01", inclusive = true, message = "Preço deve ser maior que zero")
    @Schema(description = "Preço do produto", example = "150.00")
    private BigDecimal preco;

    @Schema(description = "Identificador da categoria do produto", example = "2")
    private Long categoriaId;

    @Schema(description = "Identificador do artista do produto", example = "3")
    private Long artistaId;
	
     
    
}
