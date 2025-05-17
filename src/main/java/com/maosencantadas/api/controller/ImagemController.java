package com.maosencantadas.api.controller;

import com.maosencantadas.api.dto.ImagemDTO;
import com.maosencantadas.model.service.ImagemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/imagens")
public class ImagemController {

    private final ImagemService imagemService;

    @PostMapping("/upload")
    public ResponseEntity<ImagemDTO> uploadImagem(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "artistaId", required = false) Long artistaId,
            @RequestParam(value = "produtoId", required = false) Long produtoId,
            @RequestParam(value = "categoriaId", required = false) Long categoriaId
    ) {
        try {
            String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
            String pasta = "uploads";
            Path destino = Paths.get(pasta, nomeArquivo);

            Files.createDirectories(destino.getParent());
            file.transferTo(destino);

            ImagemDTO dto = new ImagemDTO();
            dto.setNome(nomeArquivo);
            dto.setPasta(pasta);
            dto.setArtistaId(artistaId);
            dto.setProdutoId(produtoId);

            ImagemDTO response = imagemService.salvarImagem(dto);
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            log.error("Erro ao salvar a imagem", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{nomeImagem}")
    public ResponseEntity<Resource> getImagem(@PathVariable String nomeImagem) {
        try {
            Path path = Paths.get("uploads").resolve(nomeImagem);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Erro ao servir a imagem", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/artista/{id}")
    public ResponseEntity<Resource> getImagemPorArtista(@PathVariable Long id) {
        try {
            ImagemDTO imagem = (ImagemDTO) imagemService.buscarPorArtistaId(id);
            if (imagem == null) return ResponseEntity.notFound().build();

            Path path = Paths.get("uploads").resolve(imagem.getNome());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar imagem por artista", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<Resource> getImagemPorProduto(@PathVariable Long id) {
        try {
            ImagemDTO imagem = (ImagemDTO) imagemService.buscarPorProdutoId(id);
            if (imagem == null) return ResponseEntity.notFound().build();

            Path path = Paths.get("uploads").resolve(imagem.getNome());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar imagem por produto", e);
            return ResponseEntity.status(500).build();
        }
    } 

    @GetMapping("/categoria/{id}")
    public ResponseEntity<Resource> getImagemPorCategoria(@PathVariable Long id) {
        try {
            ImagemDTO imagem = (ImagemDTO) imagemService.buscarPorCategoriaId(id);
            if (imagem == null) return ResponseEntity.notFound().build();

            Path path = Paths.get("uploads").resolve(imagem.getNome());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(path);
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Erro ao buscar imagem por categoria", e);
            return ResponseEntity.status(500).build();
        }
    }
}