package com.maosencantadas.service;

import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.Cliente;
import com.maosencantadas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente atualizarCliente(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
            .map(cliente -> {
                cliente.setNome(clienteAtualizado.getNome());
                cliente.setEndereco(clienteAtualizado.getEndereco());
                cliente.setEmail(clienteAtualizado.getEmail());
                cliente.setTelefone(clienteAtualizado.getTelefone());
                return clienteRepository.save(cliente);
            })
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id " + id));
    }

    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Cliente não encontrado com id " + id);
        }
        clienteRepository.deleteById(id);
    }
}
