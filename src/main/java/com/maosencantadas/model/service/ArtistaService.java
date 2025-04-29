package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ArtistaDTO;

import java.util.List;

public interface ArtistaService {
    List<ArtistaDTO> listarArtistas();
    ArtistaDTO buscarArtistaPorId(Long id);
    ArtistaDTO salvarArtista(ArtistaDTO artistaDTO);
    ArtistaDTO atualizarArtista(Long id, ArtistaDTO artistaAtualizado);
    void deletarArtista(Long id);
}
