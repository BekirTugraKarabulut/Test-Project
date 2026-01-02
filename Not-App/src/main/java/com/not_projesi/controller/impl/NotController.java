package com.not_projesi.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
public class NotController {

    @PostMapping("/upload")
    public ResponseEntity<?> uploadNote(@RequestParam("file") MultipartFile file) {
        try {
            File uploadDir = new File("uploads/ders-notlari-onizleme");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String fileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);

            return ResponseEntity.ok("Yüklendi: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Yükleme hatası");
        }
    }
}