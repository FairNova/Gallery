package com.blog.Gallery.repositories;



import com.blog.Gallery.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    // Дополнительные методы запросов можно писать здесь
}
