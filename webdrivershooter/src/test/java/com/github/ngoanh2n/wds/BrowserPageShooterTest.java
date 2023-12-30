package com.github.ngoanh2n.wds;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author ngoanh2n
 */
public class BrowserPageShooterTest extends AbstractBrowserTest {
    protected WebElement ignore1;
    protected WebElement ignore2;

    @Override
    @BeforeEach
    protected void openDriver(TestInfo testInfo) {
        super.openDriver(testInfo);
        driver.get("https://mvnrepository.com");

        ignore1 = driver.findElement(By.xpath("//div[@class='posts']/div[1]//h2"));
        ignore2 = driver.findElement(By.xpath("//div[@class='posts']/div[2]//h2"));
    }

    @Test
    @Order(1)
    void viewport() {
        ShooterOptions options = ShooterOptions.builder().viewport().mask(ignore1, ignore2).build();
        screenshot = WebDriverShooter.page(options, driver);
    }

    @Test
    @Order(2)
    void verticalScroll() {
        ShooterOptions options = ShooterOptions.builder().vertical().mask(ignore1, ignore2).build();
        screenshot = WebDriverShooter.page(options, driver);
    }

    @Test
    @Order(3)
    void horizontalScroll() {
        ShooterOptions options = ShooterOptions.builder().horizontal().mask(ignore1, ignore2).build();
        screenshot = WebDriverShooter.page(options, driver);
    }

    @Test
    @Order(4)
    void fullScroll() {
        ShooterOptions options = ShooterOptions.builder().full().mask(ignore1, ignore2).build();
        screenshot = WebDriverShooter.page(options, driver);
    }
}
