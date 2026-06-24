package com.example.hilife.repository;

import com.example.hilife.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    List<Gallery> findByEntityTypeAndParentEntityId(
            String entityType,
            Long parentEntityId
    );
}
