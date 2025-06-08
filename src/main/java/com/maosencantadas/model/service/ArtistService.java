package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ArtistDTO;

import java.util.List;

public interface ArtistService {

    List<ArtistDTO> findAllArtists();

    ArtistDTO findArtistById(Long id);

    List<ArtistDTO> findArtistsByCategory(Long category_id);

    ArtistDTO saveArtist(ArtistDTO artistDTO);

    ArtistDTO updateArtist(Long id, ArtistDTO artistDTO);

    void deleteArtist(Long id);
}
