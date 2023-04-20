package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Resource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Browser {
    private final WebDriver driver;
    private final Double dpr;

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
