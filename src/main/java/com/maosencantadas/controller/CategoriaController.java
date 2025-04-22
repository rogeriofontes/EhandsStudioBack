package com.maosencantadas.controller;

import com.maosencantadas.model.Categoria;
import com.maosencantadas.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id) {
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Categoria> salvarCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaService.salvarCategoria(categoria);
        return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaAtualizada) {
        Categoria categoria = categoriaService.atualizarCategoria(id, categoriaAtualizada);
        return new ResponseEntity<>(categoria, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        categoriaService.deletarCategoria(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
