package com.maosencantadas.service;

import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.Orcamento;
import com.maosencantadas.repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepository orcamentoRepository;

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
                orcamento.setImagemUrl(orcamentoAtualizado.getImagemUrl()); // <-- Atualizando imagemUrl também
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
