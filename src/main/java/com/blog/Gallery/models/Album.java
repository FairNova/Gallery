package com.blog.Gallery.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Название альбома
    private String title;

    // Описание альбома (опционально)
    private String description;

    // Отношение: один альбом – много фотографий.
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Photo> photos = new ArrayList<>();

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    // Удобный метод для добавления фотографии
    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setAlbum(this);
    }

    // Метод для удаления фотографии
    public void removePhoto(Photo photo) {
        photos.remove(photo);
        photo.setAlbum(null);
    }
}
