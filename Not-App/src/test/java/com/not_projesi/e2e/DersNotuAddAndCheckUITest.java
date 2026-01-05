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
public class DersNotuAddAndCheckUITest {

    @Order(7)
    @Test
    void registerLoginAddDersNotuAndCheckNotlarimThenLogout() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            String randomUsername = "user" + UUID.randomUUID().toString().substring(0, 8);
            String randomEmail = "email" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";

            driver.get("http://localhost:5173/register");
            driver.findElement(By.name("username")).sendKeys(randomUsername);
            driver.findElement(By.name("ogrenciSifre")).sendKeys("123456");
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Selenium7");
            driver.findElement(By.name("ogrenciSoyadi")).sendKeys("Test");
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
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Selenium7");
            driver.findElement(By.name("ogrenciSoyadi")).sendKeys("Test");
            driver.findElement(By.name("ogrenciEmail")).sendKeys(randomEmail);
            driver.findElement(By.name("bolumId")).sendKeys("1");
            driver.findElement(By.tagName("button")).click();

            wait.until(ExpectedConditions.urlContains("/home"));

            WebElement dersNotuBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnDersNotuEkle")));
            dersNotuBtn.click();
            wait.until(ExpectedConditions.urlContains("/ders-notu-ekle"));

            String randomDersNotAdi = "Test Notu " + UUID.randomUUID().toString().substring(0, 4);
            driver.findElement(By.name("dersNotAdi")).sendKeys(randomDersNotAdi);
            driver.findElement(By.name("dersNotIcerik")).sendKeys("Bu test ders notudur.");
            driver.findElement(By.name("dersNotFiyat")).sendKeys("100");
            driver.findElement(By.name("notTuruId")).sendKeys("1");

            String pdfFilePath = "C:\\Users\\bekir\\Desktop\\Document 3 (1) 1 (7).pdf";
            String pdfOnizlemePath = "C:\\Users\\bekir\\Desktop\\Document 3 (1) 1 (8).pdf";
            driver.findElement(By.name("pdfFile")).sendKeys(pdfFilePath);
            driver.findElement(By.name("pdfOnizlemeFile")).sendKeys(pdfOnizlemePath);

            WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));
            submitBtn.click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

            driver.get("http://localhost:5173/home");
            WebElement notlarimBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnNotlarim")));
            notlarimBtn.click();
            wait.until(ExpectedConditions.urlContains("/notlarim"));

            WebElement addedNote = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='" + randomDersNotAdi + "']"))
            );

            assert addedNote != null : "Ders notu Notlarım sayfasında görünmüyor!";

            driver.get("http://localhost:5173/home");
            WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Çıkış']")));
            logoutBtn.click();
            wait.until(ExpectedConditions.urlContains("/login"));

        } finally {
            driver.quit();
        }
    }
}
