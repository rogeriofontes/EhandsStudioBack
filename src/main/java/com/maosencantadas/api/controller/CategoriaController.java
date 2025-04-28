package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.CategoriaDTO;
import com.maosencantadas.model.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/v1/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Categoria", description = "Operações relacionadas a categorias de produtos")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        log.info("Listando todas as categorias");
        List<CategoriaDTO> categorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CategoriaDTO> buscarCategoriaPorId(@PathVariable Long id) {
        log.info("Buscando categoria com id: {}", id);
        CategoriaDTO categoriaDTO = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.of(Optional.of(categoriaDTO));
    }

    @PostMapping
    @Operation(summary = "Criar uma nova categoria", description = "Cria e retorna uma nova categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CategoriaDTO> criarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        log.info("Criando nova categoria: {}", categoriaDTO.getNome());
        CategoriaDTO novaCategoria = categoriaService.salvarCategoria(categoriaDTO);
        URI location = URI.create(String.format("/v1/categorias/%s", novaCategoria.getId()));
        return ResponseEntity.created(location).body(novaCategoria);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma categoria", description = "Atualiza uma categoria existente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                         content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoriaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CategoriaDTO> atualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        log.info("Atualizando categoria com id: {}", id);
        CategoriaDTO categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaDTO);
        return ResponseEntity.ok(categoriaAtualizada);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma categoria", description = "Exclui uma categoria existente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        log.info("Deletando categoria com id: {}", id);
        categoriaService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}

