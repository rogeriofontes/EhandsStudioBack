package com.maosencantadas.service;

import com.maosencantadas.domain.categoria.Categoria;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias() {
        log.info("Listando todas as categorias");
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaPorId(Long id) {
        log.info("Buscando categoria pelo id: {}", id);
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id " + id));
    }

    public Categoria salvarCategoria(Categoria categoria) {
        log.info("Salvando nova categoria: {}", categoria.getNome());
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long id, Categoria categoriaAtualizada) {
        log.info("Atualizando categoria com id: {}", id);
        return categoriaRepository.findById(id)
            .map(categoria -> {
                categoria.setNome(categoriaAtualizada.getNome());
                return categoriaRepository.save(categoria);
            })
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id " + id));
    }

    public void deletarCategoria(Long id) {
        log.info("Deletando categoria com id: {}", id);
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada com id " + id);
        }
        categoriaRepository.deleteById(id);
    }
}

