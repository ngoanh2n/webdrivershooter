package com.github.ngoanh2n.wds.driver;

import com.github.ngoanh2n.RuntimeError;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;

/**
 * @author ngoanh2n
 */
public abstract class SeleniumDriverProvider {
    public static WebDriver startDriver(String browser) {
        return switch (browser) {
            case "chrome" -> WebDriverManager.chromedriver().create();
            case "safari" -> WebDriverManager.safaridriver().create();
            case "firefox" -> WebDriverManager.firefoxdriver().create();
            case "edge" -> WebDriverManager.edgedriver().create();
            case "opera" -> WebDriverManager.operadriver().create();
            case "ie" -> WebDriverManager.iedriver().create();
            default -> throw new RuntimeError("Unknown browser: " + browser);
        };
    }
}
