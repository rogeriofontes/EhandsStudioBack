package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.product.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTagRepository extends JpaRepository<ProductTag, Long> {
    Optional<ProductTag> findByName(String name);
}
