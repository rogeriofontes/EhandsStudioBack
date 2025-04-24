package com.maosencantadas.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.maosencantadas.domain.cliente.Cliente;
import com.maosencantadas.dto.ClienteDTO;

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
