package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.EnabledIfProperty;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author ngoanh2n
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIfProperty(name = "wds.browser", value = {"chrome", "safari", "firefox", "edge"})
public class SeleniumElementShooterTest extends AbstractSeleniumTest {
    protected WebElement element;
    protected WebElement ignore1;
    protected WebElement ignore2;

    @Override
    @BeforeEach
    protected void openDriver(TestInfo testInfo) {
        super.openDriver(testInfo);
        driver.get("https://mvnrepository.com");

        element = driver.findElement(By.cssSelector("div[class='content']"));
        ignore1 = driver.findElement(By.xpath("//div[@class='posts']/div[1]//h2"));
        ignore2 = driver.findElement(By.xpath("//div[@class='posts']/div[2]//h2"));
    }

    @Test
    @Order(1)
    void viewport() {
        ShooterOptions options = ShooterOptions.builder().shootViewport().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.element(options, element, driver);
    }

    @Test
    @Order(2)
    void verticalScroll() {
        ShooterOptions options = ShooterOptions.builder().shootVerticalScroll().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.element(options, element, driver);
    }

    @Test
    @Order(3)
    void horizontalScroll() {
        ShooterOptions options = ShooterOptions.builder().shootHorizontalScroll().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.element(options, element, driver);
    }

    @Test
    @Order(4)
    void fullScroll() {
        ShooterOptions options = ShooterOptions.builder().shootFullScroll().maskElements(ignore1, ignore2).build();
        screenshot = WebDriverShooter.element(options, element, driver);
    }
}
