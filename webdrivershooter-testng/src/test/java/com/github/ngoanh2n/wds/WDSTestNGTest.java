package com.github.ngoanh2n.wds;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public abstract class WDSTestNGTest {
    protected static WebDriver driver;

    protected static void createWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterMethod
    protected void afterMethod() {
        Assert.assertNotNull(WebDriverShooter.getDriver());
    }

    @AfterClass
    protected void afterClass() {
        Assert.assertNotNull(WebDriverShooter.getDriver());
        driver.quit();
        Assert.assertThrows(ShooterException.ClosedDriverProvided.class, WebDriverShooter::getDriver);
        driver = null;
    }
}
