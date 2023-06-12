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
public class SeleniumFrameShooterTest extends AbstractSeleniumTest {
    protected WebElement frame;
    protected By ignore1;
    protected By ignore2;

    @Override
    @BeforeEach
    protected void openDriver(TestInfo testInfo) {
        super.openDriver(testInfo);
        driver.get("https://frontendresource.com/iframe-generator");

        scrollTo(By.cssSelector("#f-url")).clear();
        scrollTo(By.cssSelector("#f-url")).sendKeys("https://mvnrepository.com");
        scrollTo(By.cssSelector("label[class='radio']")).click();
        scrollTo(By.cssSelector("#g-btn")).click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        frame = driver.findElement(By.cssSelector("iframe[src='https://mvnrepository.com']"));
        ignore1 = By.xpath("//div[@class='posts']/div[1]//h2");
        ignore2 = By.xpath("//div[@class='posts']/div[2]//h2");
    }

    @Test
    @Order(1)
    void viewport() {
        ShooterOptions options = ShooterOptions.builder().shootViewport().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.frame(options, frame, driver);
    }

    @Test
    @Order(2)
    void verticalScroll() {
        ShooterOptions options = ShooterOptions.builder().shootVerticalScroll().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.frame(options, frame, driver);
    }

    @Test
    @Order(3)
    void horizontalScroll() {
        ShooterOptions options = ShooterOptions.builder().shootHorizontalScroll().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.frame(options, frame, driver);
    }

    @Test
    @Order(4)
    void fullScroll() {
        ShooterOptions options = ShooterOptions.builder().shootFullScroll().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.frame(options, frame, driver);
    }
}
