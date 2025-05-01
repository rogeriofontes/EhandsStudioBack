package com.maosencantadas.spring.files.upload.service;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {
    @PostConstruct
    void init();

    void save(MultipartFile file);

    Resource load(String filename);

    Stream<Path> loadAll();

    void deleteAll();
}
