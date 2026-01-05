package com.not_projesi.e2e;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterLoginProfileUpdateUITest {

    @Order(4)
    @Test
    void registerLoginAndUpdateProfile() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

            WebElement registerMsg = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("registerMessage"))
            );
            assert registerMsg.getText().toLowerCase().contains("kayıt");

            driver.get("http://localhost:5173/login");
            driver.findElement(By.name("username")).sendKeys(randomUsername);
            driver.findElement(By.name("ogrenciSifre")).sendKeys("123456");
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Test");
            driver.findElement(By.name("ogrenciSoyadi")).sendKeys("User");
            driver.findElement(By.name("ogrenciEmail")).sendKeys(randomEmail);
            driver.findElement(By.name("bolumId")).sendKeys("1");
            driver.findElement(By.tagName("button")).click();

            wait.until(ExpectedConditions.urlContains("/home"));

            driver.get("http://localhost:5173/profile-update");

            WebElement adi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ogrenciAdi")));
            WebElement soyadi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ogrenciSoyadi")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ogrenciEmail")));
            WebElement bolumAdi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("bolumAdi")));
            WebElement fakulteAdi = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("fakulteAdi")));

            String newRandomEmail = "updated" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";

            adi.clear();
            adi.sendKeys("Ahmet Arif");
            soyadi.clear();
            soyadi.sendKeys("Yılmaz");
            emailField.clear();
            emailField.sendKeys(newRandomEmail);
            bolumAdi.clear();
            bolumAdi.sendKeys("Yazılım");
            fakulteAdi.clear();
            fakulteAdi.sendKeys("Mühendislik");

            driver.findElement(By.cssSelector("button[type='submit']")).click();

            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

        } finally {
            driver.quit();
        }
    }
}
