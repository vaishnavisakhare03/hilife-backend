package com.example.hilife.service;

import com.example.hilife.entity.Gallery;
import com.example.hilife.repository.GalleryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final GalleryRepository galleryRepository;
    private final S3Service s3Service;

    public Gallery uploadImage(
            MultipartFile file,
            String entityType,
            Long parentEntityId,
            String postedBy
    ) throws Exception {

        String imageUrl =
                s3Service.uploadFile(file);

        Gallery gallery = new Gallery();

        gallery.setImageUrl(imageUrl);
        gallery.setEntityType(entityType);
        gallery.setParentEntityId(parentEntityId);
        gallery.setPostedBy(postedBy);

        return galleryRepository.save(gallery);
    }

    public void updateParentEntity(
            Long galleryId,
            Long parentId
    ) {

        Gallery gallery =
                galleryRepository.findById(galleryId)
                        .orElseThrow(() ->
                                new RuntimeException("Image not found"));

        gallery.setParentEntityId(parentId);

        galleryRepository.save(gallery);
    }
}