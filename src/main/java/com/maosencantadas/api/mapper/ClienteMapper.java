package com.maosencantadas.api.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.maosencantadas.api.dto.ClienteDTO;
import com.maosencantadas.model.domain.cliente.Cliente;

@Component
public class ClienteMapper {

    private final ModelMapper modelMapper;

    public ClienteMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Método para mapear de Cliente para ClienteDTO
    public ClienteDTO toDTO(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    // Método para mapear de ClienteDTO para Cliente
    public Cliente toEntity(ClienteDTO clienteDTO) {
        return modelMapper.map(clienteDTO, Cliente.class);
    }
}
