package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> listarClientes();

    ClienteDTO buscarClientePorId(Long id);

    ClienteDTO salvarCliente(ClienteDTO clienteDTO);

    ClienteDTO atualizarCliente(Long id, ClienteDTO clienteAtualizado);

    void deletarCliente(Long id);
}
