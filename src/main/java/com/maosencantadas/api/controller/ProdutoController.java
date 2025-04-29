package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ProdutoDTO;
import com.maosencantadas.model.service.ProdutoService;
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

@Slf4j
@RestController
@RequestMapping("/v1/produtos")
@CrossOrigin(origins = "*")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Listar todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listarProdutos() {
        List<ProdutoDTO> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Listar produtos por artista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos do artista retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado",
                    content = @Content)
    })
    @GetMapping("/artista/{artistaId}")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorArtista(@PathVariable Long artistaId) {
        List<ProdutoDTO> produtos = produtoService.buscarPorArtista(artistaId);
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Listar produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos da categoria retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produtos da categoria não encontrado",
                    content = @Content)
    })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProdutoDTO>> listarProdutosPorCategoria(@PathVariable Long categoriaId) {
        List<ProdutoDTO> produtos = produtoService.buscarporCategoria(categoriaId);
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Buscar um produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarProdutoPorId(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @Operation(summary = "Criar um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ProdutoDTO> criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.salvarProduto(produtoDTO);
        URI location = URI.create(String.format("/v1/produtos/%s", novoProduto.getId()));
        return ResponseEntity.created(location).body(novoProduto);
    }

    @Operation(summary = "Atualizar um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Deletar um produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
