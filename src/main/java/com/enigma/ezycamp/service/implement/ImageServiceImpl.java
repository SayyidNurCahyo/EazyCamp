package com.enigma.ezycamp.service.implement;

import com.enigma.ezycamp.repository.ImageRepository;
import com.enigma.ezycamp.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final Path IMAGE_PATH;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, @Value("ezycamp.multipart.path-location.image") String imagePath) {
        this.imageRepository = imageRepository;
        this.IMAGE_PATH = Paths.get(imagePath);
    }

    @PostConstruct
    private void createDir() {
        if(!Files.exists(IMAGE_PATH)){
            try {
                Files.createDirectory(IMAGE_PATH);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Gagal membuat direktori gambar");
            }
        }
    }

    @Override
    public Image addImage(MultipartFile image) {
        try {
            if(!List.of("image/jpeg", "image/jpg", "image/png").contains(image.getContentType())) throw new ConstraintViolationException("Format file gambar tidak valid", null);
            String name = System.currentTimeMillis()+"_"+image.getOriginalFilename();
            Path imagePath = IMAGE_PATH.resolve(name);
            Files.copy(image.getInputStream(),imagePath);
            Image imageSave = Image.builder().name(name).path(imagePath.toString())
                    .size(image.getSize()).contentType(image.getContentType()).build();
            return imageRepository.saveAndFlush(imageSave);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Gagal menyimpan gambar");
        }
    }
}
