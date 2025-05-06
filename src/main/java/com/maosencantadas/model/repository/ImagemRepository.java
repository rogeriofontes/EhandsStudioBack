package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maosencantadas.model.domain.imagem.Imagem;

import java.util.Collection;
import java.util.List;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long>{
    List<Imagem> findByArtistaId(Long artistaId);

    List<Imagem> findByProdutoId(Long produtoId);

    List<Imagem> findByCategoriaId(Long categoriaId);
}
