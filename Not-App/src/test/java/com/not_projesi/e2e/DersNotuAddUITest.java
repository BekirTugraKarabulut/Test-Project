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
public class DersNotuAddUITest {

    @Order(5)
    @Test
    void registerLoginAndAddDersNotu() throws InterruptedException {
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

            WebElement dersNotuBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnDersNotuEkle")));
            dersNotuBtn.click();
            wait.until(ExpectedConditions.urlContains("/ders-notu-ekle"));

            driver.findElement(By.name("dersNotAdi")).sendKeys("Test Ders Notu");
            driver.findElement(By.name("dersNotIcerik")).sendKeys("Bu bir test ders notudur.");
            driver.findElement(By.name("dersNotFiyat")).sendKeys("50");
            driver.findElement(By.name("notTuruId")).sendKeys("1");

            String pdfFilePath = "C:\\Users\\bekir\\Desktop\\Document 3 (1) 1 (7).pdf";
            String pdfOnizlemePath = "C:\\Users\\bekir\\Desktop\\Document 3 (1) 1 (8).pdf";
            driver.findElement(By.name("pdfFile")).sendKeys(pdfFilePath);
            driver.findElement(By.name("pdfOnizlemeFile")).sendKeys(pdfOnizlemePath);

            WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            submitBtn.click();

            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

        } finally {
            driver.quit();
        }
    }
}
