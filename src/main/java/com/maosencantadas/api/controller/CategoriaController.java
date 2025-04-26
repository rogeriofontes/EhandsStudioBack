package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.CategoriaDTO;
import com.maosencantadas.model.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        log.info("Listando todas as categorias");
        List<CategoriaDTO> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarCategoriaPorId(@PathVariable Long id) {
        log.info("Buscando categoria com id: {}", id);
        CategoriaDTO categoriaDTO = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.of(Optional.of(categoriaDTO));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        log.info("Criando nova categoria: {}", categoriaDTO.getNome());
        CategoriaDTO novaCategoria = categoriaService.salvarCategoria(categoriaDTO);
        URI location = URI.create(String.format("/v1/categorias/%s", novaCategoria.getId()));
        return ResponseEntity.created(location).body(novaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        log.info("Atualizando categoria com id: {}", id);
        CategoriaDTO categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        log.info("Deletando categoria com id: {}", id);
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}

