package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.api.dto.ImagemDTO;
import com.maosencantadas.model.domain.imagem.Imagem;

@Component
public class ImagemMapper {

    private final ModelMapper modelMapper;

    public ImagemMapper (ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public ImagemDTO toDTO (Imagem imagem) {return modelMapper.map(imagem , ImagemDTO.class);}

    public Imagem toEntity (ImagemDTO imagemDTO) {return modelMapper.map(imagemDTO, Imagem.class);}
}
