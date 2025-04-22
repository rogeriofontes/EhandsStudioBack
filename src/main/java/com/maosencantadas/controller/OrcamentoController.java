package com.maosencantadas.controller;

import com.maosencantadas.model.Orcamento;
import com.maosencantadas.service.OrcamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orcamentos")
@CrossOrigin(origins = "*")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    @GetMapping
    public List<Orcamento> listarTodos() {
        return orcamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Orcamento> buscarPorId(@PathVariable Long id) {
        return orcamentoService.buscarPorId(id);
    }

    @PostMapping
    public Orcamento criar(@RequestBody Orcamento orcamento) {
        return orcamentoService.criar(orcamento);
    }

    @PutMapping("/{id}")
    public Orcamento atualizar(@PathVariable Long id, @RequestBody Orcamento orcamentoAtualizado) {
        return orcamentoService.atualizar(id, orcamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        orcamentoService.deletar(id);
    }
}
