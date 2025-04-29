package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDTO> listarCategorias();

    CategoriaDTO buscarCategoriaPorId(Long id);

    CategoriaDTO salvarCategoria(CategoriaDTO categoriaDTO);

    CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO);

    void deletarCategoria(Long id);
}
