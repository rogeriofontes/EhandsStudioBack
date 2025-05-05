package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.ProdutoDTO;
import com.maosencantadas.api.mapper.ProdutoMapper;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.domain.produto.Produto;
import com.maosencantadas.model.repository.ProdutoRepository;
import com.maosencantadas.model.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor

public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Override
    public List<ProdutoDTO> listarProdutos() {
        log.info("Listando todos os produtos");
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<ProdutoDTO> listarProdutosPorCategoria(Long categoriaId) {
        log.info("Buscando produtos pela categoria com id: {}", categoriaId);
        List<Produto> produtos = produtoRepository.findByCategoriaId(categoriaId);

        if (produtos.isEmpty()) {
            log.warn("Nenhum produto encontrado para a categoria com id: {}", categoriaId);
            throw new RecursoNaoEncontradoException("Nenhum produto encontrado para a categoria com id " + categoriaId);
        }

        return produtos.stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProdutoDTO> listarProdutosPorArtista(Long artistaId) {
            log.info("Buscando produtos do artista com id: {}", artistaId);
            List<Produto> produtos = produtoRepository.findByArtistaId(artistaId);

            if (produtos.isEmpty()) {
                log.warn("Nenhum produto encontrado para o artista com id: {}", artistaId);
                throw new RecursoNaoEncontradoException("Nenhum produto encontrado para o artista com id " + artistaId);
            }

            return produtos.stream()
                    .map(produtoMapper::toDTO)
                    .collect(Collectors.toList());
        }



    @Override
    public ProdutoDTO buscarProdutoPorId(Long id) {
        log.info("Buscando produto pelo id: {}", id);
        Optional<Produto> produto = produtoRepository.findById(id);
    
        if (produto.isEmpty()) {
            log.warn("Produto não encontrado com id: {}", id);
            throw new RecursoNaoEncontradoException("Produto não encontrado com id " + id);
        }
    
        return produtoMapper.toDTO(produto.get());
    }
    
    @Override
    public ProdutoDTO salvarProduto(ProdutoDTO produtoDTO) {
        log.info("Criando novo produto");
        Produto produto = produtoMapper.toEntity(produtoDTO);
        Produto salvo = produtoRepository.save(produto);
        return produtoMapper.toDTO(salvo);
    }

    @Override
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoAtualizadoDTO) {
        log.info("Atualizando produto com id: {}", id);
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizadoDTO.getNome());
                    produto.setDescricao(produtoAtualizadoDTO.getDescricao());
                    produto.setTamanho(produtoAtualizadoDTO.getTamanho());
                    produto.setImagemUrl(produtoAtualizadoDTO.getImagemUrl());
                    produto.setPreco(produtoAtualizadoDTO.getPreco());
                    Produto atualizado = produtoRepository.save(produto);
                    return produtoMapper.toDTO(atualizado);
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com id " + id));
    }

    @Override
    public void deletarProduto(Long id) {
        log.info("Deletando produto com id: {}", id);
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Produto não encontrado com id " + id);
        }
        produtoRepository.deleteById(id);
    }
}
