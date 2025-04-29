package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.OrcamentoDTO;
import com.maosencantadas.api.mapper.OrcamentoMapper;
import com.maosencantadas.exception.RecursoNaoEncontradoException;
import com.maosencantadas.model.domain.orcamento.Orcamento;
import com.maosencantadas.model.repository.OrcamentoRepository;

import com.maosencantadas.model.service.OrcamentoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrcamentoServiceImpl implements OrcamentoService {

    private final OrcamentoRepository orcamentoRepository;
    private final OrcamentoMapper orcamentoMapper;

    @Override
    public List<OrcamentoDTO> listarTodos() {
        log.info("Listando todos os orçamentos");
        List<Orcamento> orcamentos = orcamentoRepository.findAll();
        return orcamentos.stream()
                .map(orcamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrcamentoDTO buscarPorId(Long id) {
        log.info("Buscando orçamento pelo id: {}", id);
        Orcamento orcamento = orcamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Orçamento não encontrado com id " + id));
        return orcamentoMapper.toDTO(orcamento);
    }

    @Override
    public OrcamentoDTO criar(OrcamentoDTO orcamentoDTO) {
        log.info("Criando novo orçamento");
        Orcamento orcamento = orcamentoMapper.toEntity(orcamentoDTO);
        Orcamento salvo = orcamentoRepository.save(orcamento);
        return orcamentoMapper.toDTO(salvo);
    }

    @Override
    public OrcamentoDTO atualizar(Long id, OrcamentoDTO orcamentoAtualizadoDTO) {
        log.info("Atualizando orçamento com id: {}", id);
        return orcamentoRepository.findById(id)
                .map(orcamento -> {
                    orcamento.setStatus(orcamentoAtualizadoDTO.getStatus());
                    orcamento.setDataOrcamento(orcamentoAtualizadoDTO.getDataOrcamento());
                    orcamento.setImagemUrl(orcamentoAtualizadoDTO.getImagemUrl());
                    Orcamento atualizado = orcamentoRepository.save(orcamento);
                    return orcamentoMapper.toDTO(atualizado);
                })
                .orElseThrow(() -> new RecursoNaoEncontradoException("Orçamento não encontrado com id " + id));
    }

    @Override
    public void deletar(Long id) {
        log.info("Deletando orçamento com id: {}", id);
        if (!orcamentoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Orçamento não encontrado com id " + id);
        }
        orcamentoRepository.deleteById(id);
    }
}
