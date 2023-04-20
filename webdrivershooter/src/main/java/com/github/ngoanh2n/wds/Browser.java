package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Resource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Browser {
    private final double dpr;
    private final WebDriver driver;
    private int documentWidth = -1;
    private int documentHeight = -1;
    private int viewportWidth = -1;
    private int viewportHeight = -1;

    public Browser(WebDriver driver, ShooterOptions options) {
        this.driver = driver;
        this.dpr = getDPR(options);
    }

    public Object executeScript(String resourceName, Object... args) {
        String script = Resource.getContent(resourceName);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(script, args);
    }

    //-------------------------------------------------------------------------------//

    private double getDPR(ShooterOptions options) {
        if (!options.checkDevicePixelRatio()) {
            return 1.0;
        } else {
            Object value = executeScript("com/github/ngoanh2n/wds/GetDPR.js");
            return value instanceof Double ? (Double) value : (Long) value * 1.0;
        }
    }
}
