package com.maosencantadas.filesupload.model.service.impl;

import com.maosencantadas.filesupload.model.service.FilesStorageService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Comparator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class FilesStorageServiceImpl implements FilesStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;
    private Path root;

    @PostConstruct
    @Override
    public void init() {
        try {
            this.root = Paths.get(uploadDir);
            if (!Files.exists(root)) {
                Files.createDirectories(root); // Garante que o diretório de upload exista
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!", e);
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Path targetLocation = this.root.resolve(file.getOriginalFilename());
            if (!Files.exists(targetLocation.getParent())) {
                Files.createDirectories(targetLocation.getParent()); // Cria o diretório, se necessário
            }
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage(), e);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            Files.walk(this.root)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            log.error("Falha ao deletar o arquivo: {}", path, e);
                        }
                    });
        } catch (IOException e) {
            throw new RuntimeException("Could not delete the files!", e);
        }
    }
}
