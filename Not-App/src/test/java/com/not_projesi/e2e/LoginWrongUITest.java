package com.not_projesi.e2e;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

public class LoginWrongUITest {

    @Order(1)
    @Test
    void shouldRegisterUserAndThenFailLogin() {

        WebDriver driver = new ChromeDriver();

        try {

            String randomUsername = "user" + UUID.randomUUID().toString().substring(0, 8);
            String randomEmail = "email" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";

            driver.get("http://localhost:5173/register");

            driver.findElement(By.name("username")).sendKeys(randomUsername);
            driver.findElement(By.name("ogrenciSifre")).sendKeys("123456");
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Test");
            driver.findElement(By.name("ogrenciSoyadi")).sendKeys("User");
            driver.findElement(By.name("ogrenciEmail")).sendKeys(randomEmail);
            driver.findElement(By.name("bolumId")).sendKeys("1");

            driver.findElement(By.tagName("button")).click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement registerMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("registerMessage"))
            );

            assert registerMsg.getText().toLowerCase().contains("kayıt");

            driver.get("http://localhost:5173/login");

            driver.findElement(By.name("username")).sendKeys(randomUsername);
            driver.findElement(By.name("ogrenciSifre")).sendKeys("YANLIS");
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Test");
            driver.findElement(By.name("ogrenciSoyadi")).sendKeys("User");
            driver.findElement(By.name("ogrenciEmail")).sendKeys(randomEmail);
            driver.findElement(By.name("bolumId")).sendKeys("1");
            driver.findElement(By.tagName("button")).click();

            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("loginError"))
            );

            assert error.getText().toLowerCase().contains("hat");

        } finally {
            driver.quit();
        }
    }
}
