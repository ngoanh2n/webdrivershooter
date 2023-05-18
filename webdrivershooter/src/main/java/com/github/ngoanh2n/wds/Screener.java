package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Resource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

@ParametersAreNonnullByDefault
public class Screener {
    private final double dpr;
    private final WebDriver driver;
    private final Rectangle innerRect;
    private final Rectangle outerRect;

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

        this.innerRect = new Rectangle(new Dimension(innerW, innerH));
        this.outerRect = new Rectangle(new Dimension(outerW, outerH));
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

        this.innerRect = new Rectangle(new Point(innerX, innerY), new Dimension(innerW, innerH));
        this.outerRect = new Rectangle(new Point(outerX, outerY), new Dimension(outerW, outerH));
    }

    //-------------------------------------------------------------------------------//

    public static double getDPR(boolean checkDPR, WebDriver driver) {
        if (!checkDPR) {
            return 1.0;
        } else {
            String resource = "com/github/ngoanh2n/wds/GetDevicePixelRatio.js";
            Object value = executeScript(driver, resource);
            return value instanceof Double ? (Double) value : (Long) value * 1.0;
        }
    }

    public static Object executeScript(WebDriver driver, String resource, Object... args) {
        String script = Resource.getContent(resource);
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    //-------------------------------------------------------------------------------//

    public double getDPR() {
        return dpr;
    }

    public Rectangle getInnerRect() {
        return innerRect;
    }

    public Rectangle getOuterRect() {
        return outerRect;
    }

    //-------------------------------------------------------------------------------//

    public int getViewportWidth() {
        String resource = "com/github/ngoanh2n/wds/GetViewportWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getViewportHeight() {
        String resource = "com/github/ngoanh2n/wds/GetViewportHeight.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentWidth() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentHeight() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentHeight.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentScrollX() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollX.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentScrollY() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollY.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getDocumentScrollBarWidth() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollBarWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public void scrollToPoint(Point point) {
        double x = point.x / dpr;
        double y = point.y / dpr;
        String resource = "com/github/ngoanh2n/wds/ScrollToPoint.js";
        executeScript(driver, resource, x, y);
    }

    //-------------------------------------------------------------------------------//

    public int getElementRectLeft(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectLeft.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementRectTop(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectTop.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementRectWidth(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementRectHeight(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementScrollY(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollY.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementScrollX(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollX.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementScrollWidth(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementScrollHeight(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementScrollBarWidth(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollBarWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public int getElementScrollBarHeight(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollBarHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    public void scrollElementIntoView(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/ScrollElementIntoView.js";
        executeScript(driver, resource, element);
    }

    public void scrollElementToPoint(WebElement element, Point point) {
        double x = point.x / dpr;
        double y = point.y / dpr;
        String resource = "com/github/ngoanh2n/wds/ScrollElementToPoint.js";
        executeScript(driver, resource, element, x, y);
    }
}
