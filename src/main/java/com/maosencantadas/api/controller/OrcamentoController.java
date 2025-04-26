package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.OrcamentoDTO;
import com.maosencantadas.model.service.OrcamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/orcamentos")
@CrossOrigin(origins = "*")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @GetMapping
    public ResponseEntity<List<OrcamentoDTO>> listarTodos() {
        List<OrcamentoDTO> orcamentos = orcamentoService.listarTodos();
        return ResponseEntity.ok(orcamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> buscarPorId(@PathVariable Long id) {
        OrcamentoDTO orcamento = orcamentoService.buscarPorId(id);
        return ResponseEntity.of(Optional.of(orcamento));
    }

    @PostMapping
    public ResponseEntity<OrcamentoDTO> criar(@RequestBody OrcamentoDTO orcamentoDTO) {
        OrcamentoDTO novoOrcamento = orcamentoService.criar(orcamentoDTO);
        URI location = URI.create(String.format("/v1/orcamentos/%s", novoOrcamento.getId()));
        return ResponseEntity.created(location).body(novoOrcamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> atualizar(@PathVariable Long id, @RequestBody OrcamentoDTO orcamentoAtualizado) {
        OrcamentoDTO atualizado = orcamentoService.atualizar(id, orcamentoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        orcamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

