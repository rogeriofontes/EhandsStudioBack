package com.maosencantadas.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.domain.produto.Produto;
import com.maosencantadas.dto.ProdutoDTO;

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
