package com.maosencantadas.filesupload.api.controller;

import com.maosencantadas.filesupload.model.message.ResponseMessage;
import com.maosencantadas.filesupload.model.service.FilesStorageService;
import com.maosencantadas.filesupload.model.domain.FileInfo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FilesController {

    @Autowired
    private FilesStorageService storageService;

    @Operation(summary = "Upload an image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Upload completed successfully",
                    content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "417", description = "Error when uploading",
                    content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
    })
    @PostMapping("/upload")
    public ResponseEntity<FileInfo> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            storageService.save(file);

            String filename = file.getOriginalFilename();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", filename)
                    .build().toString();

            return ResponseEntity.status(HttpStatus.OK).body(new FileInfo(filename, url));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }

    @Operation(summary = "List all available files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image list loaded successfully",
                    content = @Content(schema = @Schema(implementation = FileInfo.class)))
    })
    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "getFile", filename)
                    .build().toString();

            return new FileInfo(filename, url);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @Operation(summary = "Download a specific image by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Image found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Image not found")
    })
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Resource file = storageService.load(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
