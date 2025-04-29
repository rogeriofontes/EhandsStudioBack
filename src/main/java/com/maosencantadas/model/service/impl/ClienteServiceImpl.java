package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.ClienteDTO;
import com.maosencantadas.api.mapper.ClienteMapper;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.domain.cliente.Cliente;
import com.maosencantadas.model.repository.ClienteRepository;
import com.maosencantadas.model.service.ClienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public List<ClienteDTO> listarClientes() {
        log.info("Listando todos os clientes");
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(clienteMapper::toDTO)
                .toList();
    }

    @Override
    public ClienteDTO buscarClientePorId(Long id) {
        log.info("Buscando cliente pelo id: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id " + id));
        return clienteMapper.toDTO(cliente);
    }

    @Override
    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {
        log.info("Salvando novo cliente: {}", clienteDTO.getNome());
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        Cliente salvo = clienteRepository.save(cliente);
        return clienteMapper.toDTO(salvo);
    }

    @Override
    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteAtualizado) {
        log.info("Atualizando cliente com id: {}", id);
        Cliente atualizado = clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setEndereco(clienteAtualizado.getEndereco());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id " + id));
        return clienteMapper.toDTO(atualizado);
    }

    @Override
    public void deletarCliente(Long id) {
        log.info("Deletando cliente com id: {}", id);
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado com id " + id);
        }
        clienteRepository.deleteById(id);
    }
}
