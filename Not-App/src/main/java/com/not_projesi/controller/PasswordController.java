package com.not_projesi.controller;

import com.not_projesi.entity.Ogrenci;
import com.not_projesi.repository.OgrenciRepository;
import com.not_projesi.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5175")
public class PasswordController {

    private final OgrenciRepository ogrenciRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")

    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        System.out.println("Şifre yenileme isteği controller'a ulaştı: " + request.get("email"));

        String email = request.get("email");
        Optional<Ogrenci> userOpt = ogrenciRepository.findByOgrenciEmailIgnoreCase(email);


        if (userOpt.isPresent()) {
            Ogrenci user = userOpt.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpiration(LocalDateTime.now().plusMinutes(15));
            ogrenciRepository.save(user);

            String resetLink = "http://localhost:5175/reset-password?token=" + token;
            emailService.sendEmail(email, "Şifre Yenileme", "Şifreni yenilemek için tıkla: " + resetLink);
            return ResponseEntity.ok("E-posta gönderildi");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kullanıcı bulunamadı");
        }
    }


    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        Optional<Ogrenci> userOpt = ogrenciRepository.findByResetToken(token);

        if (userOpt.isPresent()) {
            Ogrenci user = userOpt.get();

            if (user.getTokenExpiration().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.GONE).body("Token süresi dolmuş");
            }

            user.setOgrenciSifre(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            user.setTokenExpiration(null);
            ogrenciRepository.save(user);

            return ResponseEntity.ok("Şifre başarıyla güncellendi");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Geçersiz token");
        }
    }
}
