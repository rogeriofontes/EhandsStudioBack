package com.maosencantadas.service;

import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.Categoria;
import com.maosencantadas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id " + id));
    }

    public Categoria salvarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long id, Categoria categoriaAtualizada) {
        return categoriaRepository.findById(id)
            .map(categoria -> {
                categoria.setNome(categoriaAtualizada.getNome());
                return categoriaRepository.save(categoria);
            })
            .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id " + id));
    }

    public void deletarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada com id " + id);
        }
        categoriaRepository.deleteById(id);
    }
}

