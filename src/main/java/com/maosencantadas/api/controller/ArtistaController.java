package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ArtistaDTO;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import com.maosencantadas.model.service.ArtistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Valid
@Validated
@Slf4j
@RestController
@RequestMapping("/v1/artistas")
@CrossOrigin(origins = "*")
@Tag(name = "Artista Controller", description = "Operações relacionadas aos Artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @Operation(summary = "Lista todos os artistas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
            }
    )
    @GetMapping
    public ResponseEntity<List<ArtistaDTO>> listarArtistas() {
        List<ArtistaDTO> artistaDTOS = artistaService.listarArtistas();
        return ResponseEntity.ok(artistaDTOS);
    }

    @Operation(summary = "Busca um artista pelo ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Artista encontrado"),
                    @ApiResponse(responseCode = "404", description = "Artista não encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ArtistaDTO> buscarArtistaPorId(
            @PathVariable
            @Schema(description = "ID do artista", example = "1")
            Long id) {
        ArtistaDTO artistaDTO = artistaService.buscarArtistaPorId(id);
        return ResponseEntity.of(Optional.of(artistaDTO));
    }

    @Operation(summary = "Cria um novo artista",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Artista criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
            }
    )
    @PostMapping
    public ResponseEntity<ArtistaDTO> criarArtista(
            @RequestBody
            @Schema(description = "Dados do novo artista")
            ArtistaDTO artistaDTO) {
        ArtistaDTO artistaRetornado = artistaService.salvarArtista(artistaDTO);

        log.info("Artista criado com sucesso: {}", artistaRetornado);
        URI location = URI.create(String.format("/v1/artistas/%s", artistaRetornado.getId()));
        return ResponseEntity.created(location).body(artistaRetornado);
    }

    @Operation(summary = "Atualiza um artista existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Artista atualizado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Artista não encontrado para atualização")
            }
    )
    @PutMapping("/{id}")
    public ArtistaDTO atualizarArtista(
            @PathVariable
            @Schema(description = "ID do artista a ser atualizado", example = "1")
            Long id,
            @RequestBody
            @Schema(description = "Novos dados do artista")
            ArtistaDTO artistaDTO) {
        return artistaService.atualizarArtista(id, artistaDTO);
    }

    @Operation(summary = "Deleta um artista pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Artista deletado com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Artista não encontrado para deletar")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArtista(
            @PathVariable
            @Schema(description = "ID do artista a ser deletado", example = "1")
            Long id) {
        artistaService.deletarArtista(id);
        return ResponseEntity.noContent().build();
    }
}

