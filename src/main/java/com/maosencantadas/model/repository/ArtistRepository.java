package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.artist.Artist;
import com.maosencantadas.model.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    List<Artist> findByArtistCategoryId(Long artistCategoryId);

    Optional<Artist> findByUser(User user);

    Optional<Artist> findByUserId(Long userId);

}
