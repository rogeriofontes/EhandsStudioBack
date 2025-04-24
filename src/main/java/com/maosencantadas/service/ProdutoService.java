package com.maosencantadas.service;

import com.maosencantadas.domain.produto.Produto;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
            .map(produto -> {
                produto.setNome(produtoAtualizado.getNome());
                produto.setDescricao(produtoAtualizado.getDescricao());
                produto.setTamanho(produtoAtualizado.getTamanho());
                produto.setImagemUrl(produtoAtualizado.getImagemUrl());
                produto.setPreco(produtoAtualizado.getPreco());
                return produtoRepository.save(produto);
            })
            .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com id " + id));
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com id " + id);
        }
        produtoRepository.deleteById(id);
    }
}