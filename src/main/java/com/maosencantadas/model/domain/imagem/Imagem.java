package com.maosencantadas.model.domain.imagem;

import com.maosencantadas.model.domain.artista.Artista;
import com.maosencantadas.model.domain.produto.Produto;
import com.maosencantadas.model.domain.categoria.Categoria;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Schema (name = "imagem", description = "Cadastra as imagens dos Artistas e dos Produtos")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema (description = "Identificador da imagem", example ="1")
    private Long id;

    @Schema (description = "Nome da Imagem", example ="imagem.jpg")
    private String nome;

    @Schema (description = "pasta que as imagens são guardadas", example ="/uploads")
    private String pasta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id , nullable = false")
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;
}