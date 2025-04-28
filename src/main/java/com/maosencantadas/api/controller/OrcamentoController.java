package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.OrcamentoDTO;
import com.maosencantadas.model.service.OrcamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Orçamentos", description = "API para gerenciamento de orçamentos")
public class OrcamentoController {

    private final OrcamentoService orcamentoService;

    public OrcamentoController(OrcamentoService orcamentoService) {
        this.orcamentoService = orcamentoService;
    }

    @Operation(summary = "Listar todos os orçamentos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de orçamentos retornada com sucesso",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = OrcamentoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<OrcamentoDTO>> listarTodos() {
        List<OrcamentoDTO> orcamentos = orcamentoService.listarTodos();
        return ResponseEntity.ok(orcamentos);
    }

    @Operation(summary = "Buscar um orçamento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orçamento encontrado",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = OrcamentoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Orçamento não encontrado",
                content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> buscarPorId(@PathVariable Long id) {
        OrcamentoDTO orcamento = orcamentoService.buscarPorId(id);
        return ResponseEntity.of(Optional.of(orcamento));
    }

    @Operation(summary = "Criar um novo orçamento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Orçamento criado com sucesso",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = OrcamentoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrcamentoDTO> criar(@RequestBody OrcamentoDTO orcamentoDTO) {
        OrcamentoDTO novoOrcamento = orcamentoService.criar(orcamentoDTO);
        URI location = URI.create(String.format("/v1/orcamentos/%s", novoOrcamento.getId()));
        return ResponseEntity.created(location).body(novoOrcamento);
    }

    @Operation(summary = "Atualizar um orçamento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Orçamento atualizado com sucesso",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = OrcamentoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Orçamento não encontrado",
                content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrcamentoDTO> atualizar(@PathVariable Long id, @RequestBody OrcamentoDTO orcamentoAtualizado) {
        OrcamentoDTO atualizado = orcamentoService.atualizar(id, orcamentoAtualizado);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deletar um orçamento por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Orçamento deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Orçamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        orcamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
