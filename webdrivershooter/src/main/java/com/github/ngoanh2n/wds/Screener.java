package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Resource;
import org.openqa.selenium.*;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Screener {
    private final double dpr;
    private final WebDriver driver;
    private final Screengle innerRect;
    private final Screengle outerRect;

    public Screener(boolean checkDPR, WebDriver driver) {
        this.dpr = getDPR(checkDPR, driver);
        this.driver = driver;

        int innerW = getViewportWidth();
        int innerH = getViewportHeight();

        int outerW = getDocumentWidth();
        int outerH = getDocumentHeight();
        int outerSBW = getDocumentScrollBarWidth();

        if (innerW < outerW || (innerH < outerH && innerW - outerSBW < outerW)) {
            innerH = innerH - outerSBW;
        }
        if (innerH < outerH) {
            innerW = innerW - outerSBW;
        }

        this.innerRect = Screengle.from(new Dimension(innerW, innerH));
        this.outerRect = Screengle.from(new Dimension(outerW, outerH));
    }

    public Screener(boolean checkDPR, WebDriver driver, WebElement element) {
        this.dpr = getDPR(checkDPR, driver);
        this.driver = driver;

        int innerX = getElementRectLeft(element);
        int innerY = getElementRectTop(element);
        int innerW = getElementRectWidth(element);
        int innerH = getElementRectHeight(element);

        int outerX = (int) (element.getLocation().x * dpr);
        int outerY = (int) (element.getLocation().y * dpr);
        int outerW = getElementScrollWidth(element);
        int outerH = getElementScrollHeight(element);
        int outerSBW = getElementScrollBarWidth(element);
        int outerSBH = getElementScrollBarHeight(element);

        if (innerW < outerW || (innerH < outerH && (innerW - outerSBH) < outerSBW)) {
            innerH = innerH - outerSBH;
        }
        if (innerH < outerH) {
            innerW = innerW - outerSBW;
        }

        this.innerRect = Screengle.from(new Point(innerX, innerY), new Dimension(innerW, innerH));
        this.outerRect = Screengle.from(new Point(outerX, outerY), new Dimension(outerW, outerH));
    }

    //-------------------------------------------------------------------------------//

    public static double getDPR(boolean checkDPR, WebDriver driver) {
        if (!checkDPR) {
            return 1.0;
        } else {
            Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetDevicePixelRatio.js");
            return value instanceof Double ? (Double) value : (Long) value * 1.0;
        }
    }

    public static Object executeScript(WebDriver driver, String resource, Object... args) {
        String script = Resource.getContent(resource);
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    //-------------------------------------------------------------------------------//

    public int getViewportWidth() {
        Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetViewportWidth.js");
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getViewportHeight() {
        Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetViewportHeight.js");
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentWidth() {
        Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetDocumentWidth.js");
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentHeight() {
        Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetDocumentHeight.js");
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentScrollX() {
        Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetDocumentScrollX.js");
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentScrollY() {
        Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetDocumentScrollY.js");
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentScrollBarWidth() {
        Object value = executeScript(driver, "com/github/ngoanh2n/wds/GetDocumentScrollBarWidth.js");
        int width = (int) (Double.parseDouble(value.toString()) * dpr);
        return Math.max(width, 40);
    }

    public void scrollToPoint(Point point) {
        double x = point.x / dpr;
        double y = point.y / dpr;
        executeScript(driver, "com/github/ngoanh2n/wds/ScrollToPoint.js", x, y);
    }
}
