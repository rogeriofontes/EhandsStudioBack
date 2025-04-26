package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.maosencantadas.api.dto.ProdutoDTO;
import com.maosencantadas.model.domain.produto.Produto;

@Component
public class ProdutoMapper {

    private final ModelMapper modelMapper;

    public ProdutoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProdutoDTO toDTO(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }
    
    public Produto toEntity(ProdutoDTO produtoDTO) {
        return modelMapper.map(produtoDTO, Produto.class);
    }
}
