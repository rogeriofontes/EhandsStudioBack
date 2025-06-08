package com.maosencantadas.model.service.impl;

import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.api.mapper.ArtistMapper;
import com.maosencantadas.model.domain.category.Category;
import com.maosencantadas.model.repository.ArtistRepository;
import com.maosencantadas.model.repository.CategoryRepository;
import com.maosencantadas.model.service.ArtistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j

public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ArtistDTO> findAllArtists() {
        log.info("Listing all artists");
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
            .map(artistMapper::toDTO)
            .toList();
    }

    @Override
    public ArtistDTO findArtistById(Long id) {
        log.info("Finding artist by id: {}", id);
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isEmpty()) {
            log.warn("Artist not found with id: {}", id);
            throw new ResourceNotFoundException("Artist not found with id: " + id);
        }
        return artistMapper.toDTO(artist.get());
    }

    @Override
    public List<ArtistDTO> findArtistsByCategory(Long category_id) {
        log.info("Finding for artist by category with id: {}", category_id);
        List<Artist> artists = artistRepository.findByCategoryId(category_id);
        if (artists.isEmpty()) {
            log.warn("No artists found for category with id: {}", category_id);
            throw new ResourceNotFoundException("No artists found for category with id " + category_id);
        }
        return artists.stream()
                .map(artistMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistDTO saveArtist(ArtistDTO artistDTO) {
        log.info("Saving new artist: {}", artistDTO.getName());

        Category category = categoryRepository.findById(artistDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + artistDTO.getCategory()));

        Artist artist = artistMapper.toEntity(artistDTO);
        artist.setCategory(category);

        Artist saved = artistRepository.save(artist);
        return artistMapper.toDTO(saved);
    }


    @Override
    public ArtistDTO updateArtist(Long id, ArtistDTO artistDTO) {
        log.info("Updating artist with id: {}", id);

        Category category = categoryRepository.findById(artistDTO.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + artistDTO.getCategory()));

        Artist artistUpdated = artistRepository.findById(id)
                .map(artist -> {
                    artist.setName(artistDTO.getName());
                    artist.setAddress(artistDTO.getAddress());
                    artist.setEmail(artistDTO.getEmail());
                    artist.setPhone(artistDTO.getPhone());
                    artist.setInsta(artistDTO.getInsta());
                    artist.setFace(artistDTO.getFace());
                    artist.setImageUrl(artistDTO.getImageUrl());
                    artist.setWhatsapp(artistDTO.getWhatsapp());
                    artist.setCpf(artistDTO.getCpf());
                    artist.setCategory(category);
                    return artistRepository.save(artist);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id " + id));
        return artistMapper.toDTO(artistUpdated);
    }

    @Override
    public void deleteArtist(Long id) {
        log.info("Deleting artist with id: {}", id);
        if (!artistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Artist not found with id " + id);
        }
        artistRepository.deleteById(id);
    }
}