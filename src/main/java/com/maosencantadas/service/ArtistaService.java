package com.maosencantadas.service;

import com.maosencantadas.model.Artista;
import com.maosencantadas.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistaService {

    @Autowired
    private ArtistaRepository artistaRepository;

    public List<Artista> listarArtistas() {
        return artistaRepository.findAll();
    }

    public Optional<Artista> buscarArtistaPorId(Long id) {
        return artistaRepository.findById(id);
    }

    public Artista salvarArtista(Artista artista) {
        return artistaRepository.save(artista);
    }

    public Artista atualizarArtista(Long id, Artista artistaAtualizado) {
        return artistaRepository.findById(id)
            .map(artista -> {
                artista.setNome(artistaAtualizado.getNome());
                artista.setEndereco(artistaAtualizado.getEndereco());
                artista.setCep(artistaAtualizado.getCep());
                artista.setCidade(artistaAtualizado.getCidade());
                artista.setEmail(artistaAtualizado.getEmail());
                artista.setTelefone(artistaAtualizado.getTelefone());
                artista.setInsta(artistaAtualizado.getInsta());
                artista.setFace(artistaAtualizado.getFace());
                return artistaRepository.save(artista);
            })
            .orElseThrow(() -> new RuntimeException("Artista não encontrado"));
    }

    public void deletarArtista(Long id) {
        artistaRepository.deleteById(id);
    }
}
