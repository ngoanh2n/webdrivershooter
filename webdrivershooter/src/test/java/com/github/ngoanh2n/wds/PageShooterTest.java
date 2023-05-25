package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.EnabledIfProperty;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@EnabledIfProperty(name = "wds.browser", value = {"chrome", "safari", "firefox", "edge"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PageShooterTest extends AbstractSeleniumTest {
    protected WebElement ignored1;
    protected WebElement ignored2;

    @Override
    @BeforeEach
    protected void openDriver(TestInfo testInfo) {
        super.openDriver(testInfo);
        driver.get("https://mvnrepository.com");
        ignored1 = driver.findElement(By.xpath("//div[@class='posts']/div[1]//h2"));
        ignored2 = driver.findElement(By.xpath("//div[@class='posts']/div[2]//h2"));
    }

    @Test
    @Order(1)
    void viewport() {
        ShooterOptions options = ShooterOptions.builder().shootViewport().maskElements(ignored1, ignored2).build();
        screenshot = WebDriverShooter.page(options, driver);
    }

    @Test
    @Order(2)
    void verticalScroll() {
        ShooterOptions options = ShooterOptions.builder().shootVerticalScroll().build();
        screenshot = WebDriverShooter.page(options, driver);
    }

    @Test
    @Order(3)
    void horizontalScroll() {
        ShooterOptions options = ShooterOptions.builder().shootHorizontalScroll().maskElements(ignored1, ignored2).build();
        screenshot = WebDriverShooter.page(options, driver);
    }

    @Test
    @Order(4)
    void fullScroll() {
        ShooterOptions options = ShooterOptions.builder().shootFullScroll().maskElements(ignored1, ignored2).build();
        screenshot = WebDriverShooter.page(options, driver);
    }
}
