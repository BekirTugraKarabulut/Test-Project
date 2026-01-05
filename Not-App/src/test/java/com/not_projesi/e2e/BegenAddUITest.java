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
public class BegenAddUITest {

    @Order(1)
    @Test
    void loginAndLikeAndUnlikeDersNot() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {

            String randomUsername = "user" + UUID.randomUUID().toString().substring(0, 8);
            String randomEmail = "email" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";
            String password = "123456";

            driver.get("http://localhost:5173/register");
            driver.findElement(By.name("username")).sendKeys(randomUsername);
            driver.findElement(By.name("ogrenciSifre")).sendKeys(password);
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Selenium");
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
            driver.findElement(By.name("ogrenciSifre")).sendKeys(password);
            driver.findElement(By.name("ogrenciAdi")).sendKeys("Selenium");
            driver.findElement(By.name("ogrenciSoyadi")).sendKeys("Test");
            driver.findElement(By.name("ogrenciEmail")).sendKeys(randomEmail);
            driver.findElement(By.name("bolumId")).sendKeys("1");
            driver.findElement(By.tagName("button")).click();
            wait.until(ExpectedConditions.urlContains("/home"));

            WebElement firstHeart = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("span[style*='position: absolute'][style*='cursor: pointer']"))
            );
            firstHeart.click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

            firstHeart.click();
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();

            WebElement logoutBtn = driver.findElement(By.xpath("//button[text()='Çıkış']"));
            logoutBtn.click();
            wait.until(ExpectedConditions.urlContains("/login"));

        } finally {
            driver.quit();
        }
    }
}
