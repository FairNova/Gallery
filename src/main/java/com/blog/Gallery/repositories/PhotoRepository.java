package com.blog.Gallery.repositories;



import com.blog.Gallery.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Дополнительные методы запросов можно писать здесь
}
