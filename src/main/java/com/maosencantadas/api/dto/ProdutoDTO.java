package com.maosencantadas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProdutoDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String tamanho;
    private String imagemUrl;
    private BigDecimal preco;
    private Long categoriaId;
    private Long artistaId;
}