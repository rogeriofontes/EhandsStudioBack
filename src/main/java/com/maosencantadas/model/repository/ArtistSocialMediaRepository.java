package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.artist.ArtistSocialMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistSocialMediaRepository extends JpaRepository<ArtistSocialMedia, Long> {
    Optional<ArtistSocialMedia> findByArtistId(Long artistId);
}
