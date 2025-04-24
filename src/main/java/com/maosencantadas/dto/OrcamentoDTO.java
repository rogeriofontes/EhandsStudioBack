package com.maosencantadas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class OrcamentoDTO {

    private Long id;
    private String status;
    private LocalDateTime dataOrcamento;
    private String imagemUrl;
    private Long clienteId;
    private Long produtoId;
}
