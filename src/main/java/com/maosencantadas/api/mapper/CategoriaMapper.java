package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.maosencantadas.api.dto.CategoriaDTO;
import com.maosencantadas.model.domain.categoria.Categoria;

@Component
public class CategoriaMapper {

    private final ModelMapper modelMapper;

    public CategoriaMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    
    public CategoriaDTO toDTO(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public Categoria toEntity(CategoriaDTO categoriaDTO) {
        return modelMapper.map(categoriaDTO, Categoria.class);
    }
}
