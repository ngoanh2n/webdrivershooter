package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.EnabledIfProperty;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author ngoanh2n
 */
@EnabledIfProperty(name = "wdc.group", value = "extension")
public abstract class WDSJUnit5Test {
    protected static WebDriver driver;

    @AfterAll
    protected static void afterAll() {
        Assertions.assertNotNull(WebDriverShooter.getDriver());
        driver.quit();
        Assertions.assertThrows(ShooterException.ClosedDriverProvided.class, WebDriverShooter::getDriver);
        driver = null;
    }

    protected static void createWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    protected void afterEach() {
        Assertions.assertNotNull(WebDriverShooter.getDriver());
    }
}
