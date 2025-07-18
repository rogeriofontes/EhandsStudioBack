package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.artist.ArtistCategory;

import java.util.List;
import java.util.Optional;

public interface ArtistCategoryService {

    List<ArtistCategory> findAll();

    ArtistCategory findById(Long id);

    ArtistCategory save(ArtistCategory artistCategory);

    ArtistCategory update(Long id, ArtistCategory artistCategory);

    void delete(Long id);

    Optional<ArtistCategory> findByName(String categoryName);

    boolean existsById(Long categoryId);
}
