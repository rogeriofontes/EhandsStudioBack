package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ArtistDTO;

import java.util.List;

public interface ArtistService {

    List<ArtistDTO> findAll();

    ArtistDTO findById(Long id);

    List<ArtistDTO> findByCategoryId(Long categoryId);

    ArtistDTO save(ArtistDTO artistDTO);

    ArtistDTO update(Long id, ArtistDTO artistDTO);

    void delete(Long id);

    List<ArtistDTO> findByCategoryName(String categoryName);

    ArtistDTO findByUserId(Long userId);
}
