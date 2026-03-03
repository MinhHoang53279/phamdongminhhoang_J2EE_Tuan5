package com.example.demo.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@Service
public class ImageStorageService {
    private final Path uploadPath;

    public ImageStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(uploadPath);
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        int extensionIndex = originalName.lastIndexOf('.');
        if (extensionIndex >= 0) {
            extension = originalName.substring(extensionIndex);
        }

        String savedName = UUID.randomUUID() + extension;
        Path destination = uploadPath.resolve(savedName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        return savedName;
    }

    public void deleteImage(String imageName) {
        if (imageName == null || imageName.isBlank()) {
            return;
        }

        Path imagePath = uploadPath.resolve(imageName).normalize();
        try {
            Files.deleteIfExists(imagePath);
        } catch (IOException ignored) {
        }
    }

    public boolean isAllowedImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return true;
        }

        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
