package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maosencantadas.model.domain.category.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
