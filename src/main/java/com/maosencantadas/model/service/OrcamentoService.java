package com.maosencantadas.model.service;

import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.domain.orcamento.Orcamento;
import com.maosencantadas.model.repository.OrcamentoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;

    public List<Orcamento> listarTodos() {
        return orcamentoRepository.findAll();
    }

    public Optional<Orcamento> buscarPorId(Long id) {
        return orcamentoRepository.findById(id);
    }

    public Orcamento criar(Orcamento orcamento) {
        return orcamentoRepository.save(orcamento);
    }

    public Orcamento atualizar(Long id, Orcamento orcamentoAtualizado) {
        return orcamentoRepository.findById(id)
            .map(orcamento -> {
                orcamento.setStatus(orcamentoAtualizado.getStatus());
                orcamento.setDataOrcamento(orcamentoAtualizado.getDataOrcamento());
                orcamento.setImagemUrl(orcamentoAtualizado.getImagemUrl());
                return orcamentoRepository.save(orcamento);
            })
            .orElseThrow(() -> new RecursoNaoEncontradoException("Orçamento não encontrado com id " + id));
    }

    public void deletar(Long id) {
        if (!orcamentoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Orçamento não encontrado com id " + id);
        }
        orcamentoRepository.deleteById(id);
    }
}