package com.maosencantadas.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.domain.orcamento.Orcamento;
import com.maosencantadas.dto.OrcamentoDTO;

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
