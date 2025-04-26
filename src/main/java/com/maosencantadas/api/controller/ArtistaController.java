package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ArtistaDTO;
import com.maosencantadas.model.service.ArtistaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/artistas")
@CrossOrigin(origins = "*")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @GetMapping
    public ResponseEntity<List<ArtistaDTO>> listarArtistas() {  
        List<ArtistaDTO> artistaDTOS = artistaService.listarArtistas();
        return ResponseEntity.ok(artistaDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistaDTO> buscarArtistaPorId(@PathVariable Long id) {
        ArtistaDTO artistaDTO = artistaService.buscarArtistaPorId(id);
        return ResponseEntity.of(Optional.of(artistaDTO));
    }

    @PostMapping
    public ResponseEntity<ArtistaDTO> criarArtista(@RequestBody ArtistaDTO artistaDTO) {
        ArtistaDTO artistaRetornado = artistaService.salvarArtista(artistaDTO);

        log.info("Cliente criado com sucesso: {}", artistaRetornado);
        URI location = URI.create(String.format("/v1/artistas/%s", artistaRetornado.getId()));
        return ResponseEntity.created(location).body(artistaRetornado);
    }        
    @PutMapping("/{id}")
    public ArtistaDTO atualizarArtista(@PathVariable Long id, @RequestBody ArtistaDTO artistaDTO) {
        return artistaService.atualizarArtista(id, artistaDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArtista(@PathVariable Long id) {
        artistaService.deletarArtista(id);
        return ResponseEntity.noContent().build();
    }
}