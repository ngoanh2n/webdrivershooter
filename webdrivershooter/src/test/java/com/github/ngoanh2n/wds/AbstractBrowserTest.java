package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.EnabledIfProperty;
import com.github.ngoanh2n.Property;
import com.github.ngoanh2n.wds.driver.AppiumDriverProvider;
import com.github.ngoanh2n.wds.driver.SeleniumDriverProvider;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;

/**
 * @author ngoanh2n
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@EnabledIfProperty(name = "wds.group", value = {"selenium-local", "appium-local"})
public abstract class AbstractBrowserTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractBrowserTest.class);
    protected WebDriver driver;
    protected Screenshot screenshot;
    protected TestInfo testInfo;

    @BeforeEach
    protected void openDriver(TestInfo testInfo) {
        String browser = Property.ofString("wds.browser").getValue();
        if (browser.equals("appium")) {
            driver = AppiumDriverProvider.startDriver(null);
        } else {
            driver = SeleniumDriverProvider.createDriver(browser);
        }
        this.testInfo = testInfo;
    }

    @AfterEach
    protected void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
        if (screenshot != null) {
            String clazzName = testInfo.getTestClass().map(Class::getSimpleName).orElse(this.getClass().getSimpleName());
            String methodName = testInfo.getTestMethod().map(Method::getName).orElse(Commons.timestamp());

            BufferedImage image = screenshot.getImage();
            log.info(clazzName + "." + methodName + ": " + image.getWidth() + "x" + image.getHeight());

            screenshot.saveImage(new File(String.format("build/ngoanh2n/wds/%s.%s-image1.png", clazzName, methodName)));
            screenshot.saveMaskedImage(new File(String.format("build/ngoanh2n/wds/%s.%s-image2.png", clazzName, methodName)));
            screenshot = null;
        }
    }

    protected WebElement scrollTo(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }
}
