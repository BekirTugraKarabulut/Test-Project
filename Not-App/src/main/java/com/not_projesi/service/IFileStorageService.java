package com.not_projesi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileStorageService {

    String storeFile(MultipartFile file, String directory) throws IOException;

    void deleteFile(String filePath);


}