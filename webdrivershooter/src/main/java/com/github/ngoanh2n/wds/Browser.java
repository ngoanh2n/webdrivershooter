package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Resource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Browser {
    private final WebDriver driver;
    private final Double dpr;

    public Browser(WebDriver driver, ShooterOptions options) {
        this.driver = driver;
        this.dpr = 1.0;
    }

    public Object executeScript(String resourceName, Object... args) {
        String script = Resource.getContent(resourceName);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(script, args);
    }
}
