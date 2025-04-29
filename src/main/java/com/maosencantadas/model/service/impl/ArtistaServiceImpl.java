package com.maosencantadas.model.service.impl;

import com.maosencantadas.model.domain.artista.Artista;
import com.maosencantadas.api.dto.ArtistaDTO;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.api.mapper.ArtistaMapper;
import com.maosencantadas.model.repository.ArtistaRepository;
import com.maosencantadas.model.service.ArtistaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final ArtistaMapper artistaMapper;

    @Override
    public List<ArtistaDTO> listarArtistas() {
        log.info("Listando todos os artistas");
        List<Artista> artistas = artistaRepository.findAll();
        return artistas.stream()
            .map(artistaMapper::toDTO)
            .toList();
    }

    @Override
    public ArtistaDTO buscarArtistaPorId(Long id) {
        log.info("Buscando artista pelo id: {}", id);
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isEmpty()) {
            log.warn("Artista não encontrado com id: {}", id);
            throw new RecursoNaoEncontradoException("Artista não encontrado com id " + id);
        }

        return artistaMapper.toDTO(artista.get());
    }

    @Override
    public ArtistaDTO salvarArtista(ArtistaDTO artistaDTO) {
        log.info("Salvando novo artista: {}", artistaDTO.getNome());
        Artista artista = artistaMapper.toEntity(artistaDTO);
        Artista saved = artistaRepository.save(artista);
        return artistaMapper.toDTO(saved);
    }

    @Override
    public ArtistaDTO atualizarArtista(Long id, ArtistaDTO artistaAtualizado) {
        log.info("Atualizando artista com id: {}", id);
        Artista artistaAlterado = artistaRepository.findById(id)
                .map(artista -> {
                    artista.setNome(artistaAtualizado.getNome());
                    artista.setEndereco(artistaAtualizado.getEndereco());
                    artista.setEmail(artistaAtualizado.getEmail());
                    artista.setTelefone(artistaAtualizado.getTelefone());
                    artista.setInsta(artistaAtualizado.getInsta());
                    artista.setFace(artistaAtualizado.getFace());
                    artista.setFoto(artistaAtualizado.getFoto());
                    artista.setWhatsapp(artistaAtualizado.getWhatsapp());
                    artista.setCpf(artistaAtualizado.getCpf());
                    return artistaRepository.save(artista);
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Artista não encontrado com id " + id));

        return artistaMapper.toDTO(artistaAlterado);
    }

    @Override
    public void deletarArtista(Long id) {
        log.info("Deletando artista com id: {}", id);
        if (!artistaRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Artista não encontrado com id " + id);
        }
        artistaRepository.deleteById(id);
    }
}