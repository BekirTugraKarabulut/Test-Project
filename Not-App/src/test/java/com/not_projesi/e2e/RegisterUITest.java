package com.not_projesi.e2e;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterUITest {

    @Order(1)
    @Test
    void shouldRegisterUser() {
        WebDriver driver = new ChromeDriver();

        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        String testUsername = "testuser_" + uniqueId;
        String testEmail = "test_" + uniqueId + "@test.com";

        try {
            driver.get("http://localhost:5173/register");

            driver.findElement(By.name("username")).sendKeys(testUsername);
            driver.findElement(By.name("ogrenciSifre")).sendKeys("123456");
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Test");
            driver.findElement(By.name("ogrenciSoyadi")).sendKeys("User");
            driver.findElement(By.name("ogrenciEmail")).sendKeys(testEmail);
            driver.findElement(By.name("bolumId")).sendKeys("1");

            driver.findElement(By.tagName("button")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement msg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("registerMessage")
                    )
            );

            Assertions.assertTrue(
                    msg.getText().toLowerCase().contains("kayıt"),
                    "Kayıt başarılı mesajı görünmedi!"
            );

        } finally {
            driver.quit();
        }
    }
}