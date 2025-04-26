package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maosencantadas.model.domain.cliente.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}