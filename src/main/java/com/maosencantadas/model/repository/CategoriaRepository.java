package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maosencantadas.model.domain.categoria.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
