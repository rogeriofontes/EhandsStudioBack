package com.maosencantadas.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ClienteDTO {

    private Long id;
    private String nome;
    private String endereco;
    private String email;
    private String telefone;
}