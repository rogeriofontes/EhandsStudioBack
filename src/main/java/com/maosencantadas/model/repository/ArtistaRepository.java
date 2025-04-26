package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maosencantadas.model.domain.artista.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long> {
}