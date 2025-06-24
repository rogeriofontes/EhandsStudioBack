package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ArtistDTO;
import com.maosencantadas.model.domain.artist.Artist;

import java.util.List;

public interface ArtistService {

    List<Artist> findAll();

    Artist findById(Long id);

    List<Artist> findByCategoryId(Long categoryId);

    Artist save(Artist artist);

    Artist update(Long id, Artist artist);

    void delete(Long id);

    List<Artist> findByCategoryName(String categoryName);

    Artist findByUserId(Long userId);
}
