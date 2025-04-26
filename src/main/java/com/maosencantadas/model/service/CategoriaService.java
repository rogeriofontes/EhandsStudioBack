package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.CategoriaDTO;
import com.maosencantadas.api.mapper.CategoriaMapper;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.domain.categoria.Categoria;
import com.maosencantadas.model.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public List<CategoriaDTO> listarCategorias() {
        log.info("Listando todas as categorias");
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                .map(categoriaMapper::toDTO)
                .toList();
    }

    public CategoriaDTO buscarCategoriaPorId(Long id) {
        log.info("Buscando categoria pelo id: {}", id);
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id " + id));
        return categoriaMapper.toDTO(categoria);
    }

    public CategoriaDTO salvarCategoria(CategoriaDTO categoriaDTO) {
        log.info("Salvando nova categoria: {}", categoriaDTO.getNome());
        Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return categoriaMapper.toDTO(categoriaSalva);
    }

    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        log.info("Atualizando categoria com id: {}", id);
        Categoria categoriaAtualizada = categoriaRepository.findById(id)
                .map(categoria -> {
                    categoria.setNome(categoriaDTO.getNome());
                    return categoriaRepository.save(categoria);
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada com id " + id));

        return categoriaMapper.toDTO(categoriaAtualizada);
    }

    public void deletarCategoria(Long id) {
        log.info("Deletando categoria com id: {}", id);
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Categoria não encontrada com id " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
