package com.maosencantadas.controller;

import com.maosencantadas.model.Artista;
import com.maosencantadas.service.ArtistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artistas")
@CrossOrigin(origins = "*")
public class ArtistaController {

    @Autowired
    private ArtistaService artistaService;

    @GetMapping
    public List<Artista> listarArtistas() {
        return artistaService.listarArtistas();
    }

    @GetMapping("/{id}")
    public Optional<Artista> buscarArtista(@PathVariable Long id) {
        return artistaService.buscarArtistaPorId(id);
    }

    @PostMapping
    public Artista criarArtista(@RequestBody Artista artista) {
        return artistaService.salvarArtista(artista);
    }

    @PutMapping("/{id}")
    public Artista atualizarArtista(@PathVariable Long id, @RequestBody Artista artista) {
        return artistaService.atualizarArtista(id, artista);
    }

    @DeleteMapping("/{id}")
    public void deletarArtista(@PathVariable Long id) {
        artistaService.deletarArtista(id);
    }
}