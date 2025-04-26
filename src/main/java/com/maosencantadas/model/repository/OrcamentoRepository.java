package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maosencantadas.model.domain.orcamento.Orcamento;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
}
