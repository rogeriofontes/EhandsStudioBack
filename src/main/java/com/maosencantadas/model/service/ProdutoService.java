package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoService {
    List<ProdutoDTO> listarProdutos();

    List<ProdutoDTO> buscarPorArtista(Long artistaId);

<<<<<<< HEAD
    List<ProdutoDTO> buscarporCategoria(long categoriaId);

=======
>>>>>>> origin/main
    ProdutoDTO buscarProdutoPorId(Long id);

    ProdutoDTO salvarProduto(ProdutoDTO produtoDTO);

    ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoAtualizadoDTO);

    void deletarProduto(Long id);
}
