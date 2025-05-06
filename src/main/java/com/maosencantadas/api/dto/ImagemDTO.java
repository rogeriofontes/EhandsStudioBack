package com.maosencantadas.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImagemDTO {

    @Schema(description = "ID da imagem", example = "10")
    private Long id;

    @Schema(description = "Nome da imagem", example = "imagem.jpg")
    private String nome;

    @Schema(description = "Pasta onde a imagem está salva", example = "/uploads")
    private String pasta;

    @Schema(description = "ID do artista (opcional se imagem for de produto)", example = "1")
    private Long artistaId;

    @Schema(description = "ID do produto (opcional se imagem for de artista)", example = "2")
    private Long produtoId;

    @Schema(description = "Id da categoria (opcional se imagem for de artista", example = "3")
    private Long categoriaId;
}
