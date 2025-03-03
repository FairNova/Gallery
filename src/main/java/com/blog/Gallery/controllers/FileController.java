package com.blog.Gallery.controllers;

import com.blog.Gallery.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/uploads")
public class FileController {

    private final StorageService storageService;
    private final ServletContext servletContext;

    public FileController(StorageService storageService, ServletContext servletContext) {
        this.storageService = storageService;
        this.servletContext = servletContext;
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        Resource file = storageService.loadFileAsResource(fileName);

        // Получаем MIME-тип файла
        String contentType = getContentType(file);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))  // Устанавливаем правильный Content-Type
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")  // inline - показывает изображение, а не скачивает
                .body(file);
    }

    /**
     * Определяет MIME-тип файла
     */
    private String getContentType(Resource resource) {
        try {
            Path filePath = Paths.get(resource.getURI());
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Если неизвестно, отдаем "application/octet-stream"
        }
    }
}
