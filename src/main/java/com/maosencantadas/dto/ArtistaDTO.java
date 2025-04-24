package com.maosencantadas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ArtistaDTO {

    private Long id;
    private String nome;
    private String foto;
    private String endereco;
    private String email;
    private String telefone;
    private String insta;
    private String face;
    private String whatsapp;
}
