package com.github.ngoanh2n.wds;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public abstract class WDSJUnit5Test {
    protected static WebDriver driver;

    @AfterEach
    protected void afterEach() {
        Assertions.assertNotNull(WebDriverShooter.getDriver());
    }

    @AfterAll
    protected static void afterAll() {
        Assertions.assertNotNull(WebDriverShooter.getDriver());
        driver.quit();
    }

    protected static void createWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
}
