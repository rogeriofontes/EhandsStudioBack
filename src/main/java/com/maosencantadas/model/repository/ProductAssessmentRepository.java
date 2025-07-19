package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.product.ProductAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductAssessmentRepository extends JpaRepository<ProductAssessment, Long> {
    Optional<ProductAssessment> findByName(String name);
}
