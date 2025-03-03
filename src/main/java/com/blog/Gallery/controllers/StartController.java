package com.blog.Gallery.controllers;

import com.blog.Gallery.models.Album;
import com.blog.Gallery.models.Photo;
import com.blog.Gallery.repositories.AlbumRepository;
import com.blog.Gallery.repositories.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.blog.Gallery.storage.StorageService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/start")
public class StartController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private AlbumRepository albumRepo;

    @Autowired
    private PhotoRepository photoRepo;

    @GetMapping()
    public String start( Model model) {
        model.addAttribute("albums", albumRepo.findAll());
        return "start";
    }



    @PostMapping("/create")
    public String createAlbumWithPhoto(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/start?error=empty_file";
        }

        // 1. Сохраняем файл и получаем его имя
        String fileName = storageService.saveFile(file);

        // 2. Создаём новый альбом
        Album album = new Album();
        album.setTitle("New Album");
        albumRepo.save(album);

        // 3. Создаём фото и привязываем его к альбому
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setUrl("/uploads/" + fileName); // Ссылка на изображение
        photo.setUploadedAt(LocalDateTime.now());
        photo.setAlbum(album);
        photoRepo.save(photo);

        // 4. Перенаправляем пользователя
        return "redirect:/start";
    }





}
