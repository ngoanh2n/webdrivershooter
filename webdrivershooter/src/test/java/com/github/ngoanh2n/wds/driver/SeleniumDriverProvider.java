package com.github.ngoanh2n.wds.driver;

import com.github.ngoanh2n.Property;
import com.github.ngoanh2n.RuntimeError;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public abstract class SeleniumDriverProvider {
    public static WebDriver createDriver() {
        String browser = Property.ofString("wds.browser").getValue();

        switch (browser) {
            case "chrome":
            case "opera":
                if (browser.equals("chrome")) {
                    WebDriverManager.chromedriver().setup();
                } else {
                    WebDriverManager wdm = WebDriverManager.operadriver();
                    wdm.setup();
                    String binPath = wdm.getDownloadedDriverPath();
                    System.setProperty("webdriver.opera.driver", binPath);
                }
                return new ChromeDriver();
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "edge":
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            case "safari":
                WebDriverManager.safaridriver().setup();
                return new SafariDriver();
            case "ie":
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            default:
                throw new RuntimeError("Unknown browser: " + browser);
        }
    }
}
