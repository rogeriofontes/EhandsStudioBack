package com.maosencantadas.service;

import com.maosencantadas.domain.artista.Artista;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public List<Artista> listarArtistas() {
        log.info("Listando todos os artistas");
        return artistaRepository.findAll();
    }

    public Optional<Artista> buscarArtistaPorId(Long id) {
        log.info("Buscando artista pelo id: {}", id);
        return artistaRepository.findById(id);
    }

    public Artista salvarArtista(Artista artista) {
        log.info("Salvando novo artista: {}", artista.getNome());
        return artistaRepository.save(artista);
    }

    public Artista atualizarArtista(Long id, Artista artistaAtualizado) {
        log.info("Atualizando artista com id: {}", id);
        return artistaRepository.findById(id)
            .map(artista -> {
                artista.setNome(artistaAtualizado.getNome());
                artista.setEndereco(artistaAtualizado.getEndereco());
                artista.setEmail(artistaAtualizado.getEmail());
                artista.setTelefone(artistaAtualizado.getTelefone());
                artista.setInsta(artistaAtualizado.getInsta());
                artista.setFace(artistaAtualizado.getFace());
                artista.setFoto(artistaAtualizado.getFoto());
                return artistaRepository.save(artista);
            })
            .orElseThrow(() -> new RecursoNaoEncontradoException("Artista não encontrado com id " + id));
    }

    public void deletarArtista(Long id) {
        log.info("Deletando artista com id: {}", id);
        if (!artistaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Artista não encontrado com id " + id);
        }
        artistaRepository.deleteById(id);
    }
}