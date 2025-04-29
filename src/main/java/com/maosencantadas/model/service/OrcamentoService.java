package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.OrcamentoDTO;

import java.util.List;

public interface OrcamentoService {
    List<OrcamentoDTO> listarTodos();

    OrcamentoDTO buscarPorId(Long id);

    OrcamentoDTO criar(OrcamentoDTO orcamentoDTO);

    OrcamentoDTO atualizar(Long id, OrcamentoDTO orcamentoAtualizadoDTO);

    void deletar(Long id);
}
