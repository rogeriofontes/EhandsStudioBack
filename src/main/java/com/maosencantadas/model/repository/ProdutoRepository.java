package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maosencantadas.model.domain.produto.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}