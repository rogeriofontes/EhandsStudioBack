package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.artist.ArtistCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistCategoryRepository extends JpaRepository<ArtistCategory, Long> {
    Optional<ArtistCategory> findByName(String name);
}
