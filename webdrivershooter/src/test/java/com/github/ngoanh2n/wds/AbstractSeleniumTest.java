package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.wds.driver.SeleniumDriverProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public abstract class AbstractSeleniumTest {
    private static final Logger log = LoggerFactory.getLogger(AbstractSeleniumTest.class);
    protected WebDriver driver;
    protected Screenshot screenshot;
    protected TestInfo testInfo;

    @BeforeEach
    protected void openDriver(TestInfo testInfo) {
        driver = SeleniumDriverProvider.createDriver();
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

            screenshot.saveImage(new File(String.format("build/ngoanh2n/wds/%s-%s-image.png", clazzName, methodName)));
            screenshot.saveMaskedImage(new File(String.format("build/ngoanh2n/wds/%s-%s-maskedImage.png", clazzName, methodName)));
            screenshot = null;
        }
    }
}
