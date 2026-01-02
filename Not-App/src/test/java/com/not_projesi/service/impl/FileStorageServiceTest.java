package com.not_projesi.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FileStorageServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void storeFile_dosyayiKaydederVeYolDonderir() throws Exception {

        FileStorageService service = new FileStorageService();

        ReflectionTestUtils.setField(service, "uploadDir", tempDir.toString());

        MultipartFile file = Mockito.mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("deneme.pdf");

        byte[] content = "merhaba".getBytes();
        InputStream is = new ByteArrayInputStream(content);
        when(file.getInputStream()).thenReturn(is);

        String returnedPath = service.storeFile(file, "ogrenciler");

        assertTrue(returnedPath.startsWith("ogrenciler/"));
        assertTrue(returnedPath.endsWith(".pdf"));

        Path storedFile = tempDir.resolve(returnedPath);
        assertTrue(Files.exists(storedFile));

        assertEquals("merhaba", Files.readString(storedFile));
    }

    @Test
    void deleteFile_varOlanDosyayiSiler() throws Exception {

        FileStorageService service = new FileStorageService();
        ReflectionTestUtils.setField(service, "uploadDir", tempDir.toString());

        Path file = tempDir.resolve("test/abc.txt");
        Files.createDirectories(file.getParent());
        Files.writeString(file, "sil beni");

        assertTrue(Files.exists(file));

        service.deleteFile("test/abc.txt");

        assertFalse(Files.exists(file));
    }
}
