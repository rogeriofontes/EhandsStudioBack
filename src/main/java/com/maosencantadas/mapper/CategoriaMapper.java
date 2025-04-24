package com.maosencantadas.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.domain.categoria.Categoria;
import com.maosencantadas.dto.CategoriaDTO;

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
