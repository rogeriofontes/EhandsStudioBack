package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maosencantadas.model.domain.image.Image;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{

    List<Image> findByArtistId(Long artistId);

    List<Image> findByProductId(Long productId);

    List<Image> findByCategoryId(Long categoryId);
}
