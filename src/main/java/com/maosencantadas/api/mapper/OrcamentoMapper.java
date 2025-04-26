package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.maosencantadas.api.dto.OrcamentoDTO;
import com.maosencantadas.model.domain.orcamento.Orcamento;

@Component
public class OrcamentoMapper {

    private final ModelMapper modelMapper;

    public OrcamentoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrcamentoDTO toDTO(Orcamento orcamento) {
        return modelMapper.map(orcamento, OrcamentoDTO.class);
    }

    public Orcamento toEntity(OrcamentoDTO orcamentoDTO) {
        return modelMapper.map(orcamentoDTO, Orcamento.class);
    }
}
