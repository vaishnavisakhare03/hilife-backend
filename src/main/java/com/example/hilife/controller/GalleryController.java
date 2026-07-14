package com.example.hilife.controller;

import com.example.hilife.entity.Gallery;
import com.example.hilife.service.GalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gallery")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class GalleryController {

    private final GalleryService galleryService;

    @PostMapping("/upload")
    public Gallery uploadImage(

            @RequestParam MultipartFile file,

            @RequestParam String entityType,

            @RequestParam Long parentEntityId,

            @RequestParam String postedBy

    ) throws Exception {

        return galleryService.uploadImage(
                file,
                entityType,
                parentEntityId,
                postedBy
        );
    }

    @PutMapping("/{galleryId}/parent/{parentId}")
    public void updateParent(
            @PathVariable Long galleryId,
            @PathVariable Long parentId
    ) {
        galleryService.updateParentEntity(
                galleryId,
                parentId
        );
    }
}