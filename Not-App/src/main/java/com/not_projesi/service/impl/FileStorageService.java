package com.not_projesi.service.impl;

import com.not_projesi.service.IFileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService implements IFileStorageService {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file, String directory) throws IOException {

        String fileDirectory = uploadDir + File.separator + directory;
        File dirPath = new File(fileDirectory);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        Path targetLocation = Paths.get(fileDirectory + File.separator + fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return directory + "/" + fileName;
    }

    @Override
    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(uploadDir + File.separator + filePath);
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Dosya silinemedi: " + filePath, e);
        }
    }

}