package com.maosencantadas.domain.produto;

import jakarta.persistence.*;
import java.math.BigDecimal;

import com.maosencantadas.domain.artista.Artista;
import com.maosencantadas.domain.categoria.Categoria;

import jakarta.validation.constraints.NotNull;
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
public class Produto {
		
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String nome;
    private String descricao;
    private String tamanho;
    private String imagemUrl;
    private BigDecimal preco;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;
	
	@NotNull
    @ManyToOne
    @JoinColumn(name = "artista_id", referencedColumnName = "id")
    private Artista artista;
	
     
    
}
