package com.blog.Gallery.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class StorageService {

    private static final String UPLOAD_DIR = "/app/uploads"; // Папка внутри контейнера
    private static final String URL_PREFIX = "/uploads/";   // HTTP-доступ

    public StorageService() {
        init();
    }

    // Создаём папку при запуске, если её нет
    private void init() {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать папку для загрузки файлов!", e);
        }
    }

    /**
     * Сохраняет загруженный файл и возвращает URL для Thymeleaf.
     */
    public String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Файл пуст!");
        }

        try {
            // Генерируем уникальное имя файла
            String extension = getFileExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + "." + extension;

            // Полный путь сохранения файла
            Path destinationPath = Paths.get(UPLOAD_DIR).resolve(fileName);
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            return URL_PREFIX + fileName; // Теперь URL = "/uploads/имя_файла"
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения файла!", e);
        }
    }

    /**
     * Получает ресурс загруженного файла (для контроллера).
     */
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Файл не найден: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Ошибка загрузки файла!", e);
        }
    }

    /**
     * Получает расширение файла.
     */
    private String getFileExtension(String fileName) {
        return fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1) : "jpg";
    }
}
