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

    //-------------------------------------------------------------------------------//

    public int getDocumentWidth() {
        if (documentWidth == -1) {
            Object value = executeScript("com/github/ngoanh2n/wds/GetDocumentWidth.js");
            documentWidth = (int) (Double.parseDouble(value.toString()) * dpr);
        }
        return documentWidth;
    }

    public int getDocumentHeight() {
        if (documentHeight == -1) {
            Object value = executeScript("com/github/ngoanh2n/wds/GetDocumentHeight.js");
            documentHeight = (int) (Double.parseDouble(value.toString()) * dpr);
        }
        return documentHeight;
    }

    public int getViewportWidth() {
        if (viewportWidth == -1) {
            Object value = executeScript("com/github/ngoanh2n/wds/GetViewportWidth.js");
            viewportWidth = (int) (Double.parseDouble(value.toString()) * dpr);
        }
        return viewportWidth;
    }

    public int getViewportHeight() {
        if (viewportHeight == -1) {
            Object value = executeScript("com/github/ngoanh2n/wds/GetViewportHeight.js");
            viewportHeight = (int) (Double.parseDouble(value.toString()) * dpr);
        }
        return viewportHeight;
    }

    public int getScrollBarWidth() {
        Object value = executeScript("com/github/ngoanh2n/wds/GetScrollBarWidth.js");
        int width = (int) (Double.parseDouble(value.toString()) * dpr);
        return Math.max(width, 40);
    }

    public int getCurrentScrollX() {
        Object value = executeScript("com/github/ngoanh2n/wds/GetCurrentScrollX.js");
        return (int) (Double.parseDouble(value.toString()) * dpr);
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
