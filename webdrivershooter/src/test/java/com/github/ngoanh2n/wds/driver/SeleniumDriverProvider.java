package com.github.ngoanh2n.wds.driver;

import com.github.ngoanh2n.RuntimeError;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * @author ngoanh2n
 */
public abstract class SeleniumDriverProvider {
    public static WebDriver createDriver(String browser) {
        return switch (browser) {
            case "chrome" -> new ChromeDriver();
            case "safari" -> new SafariDriver();
            case "edge" -> new EdgeDriver();
            case "firefox" -> new FirefoxDriver();
            case "ie" -> new InternetExplorerDriver();
            default -> throw new RuntimeError("Unknown browser: " + browser);
        };
    }
}
