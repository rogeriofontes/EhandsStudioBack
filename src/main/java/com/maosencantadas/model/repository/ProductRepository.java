package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.maosencantadas.model.domain.product.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByArtistId(Long artistId);

    List<Product> findByCategoryId(long categoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN FETCH p.category")
    List<Product> findAllWithArtistAndCategory();

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN FETCH p.category WHERE p.category.id = :categoryId")
    List<Product> findByCategoryIdWithArtist(Long categoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN FETCH p.category WHERE p.artist.id = :artistId")
    List<Product> findByArtistIdWithCategory(Long artistId);
}