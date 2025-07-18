package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByArtistId(Long artistId);

    List<Product> findByProductCategoryId(long productCategoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN FETCH p.productCategory")
    List<Product> findAllWithArtistAndCategory();

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN FETCH p.productCategory WHERE p.productCategory.id = :productCategoryId")
    List<Product> findByProductCategoryIdWithArtist(Long productCategoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.artist JOIN FETCH p.productCategory WHERE p.artist.id = :artistId")
    List<Product> findByArtistIdWithCategory(Long artistId);
}