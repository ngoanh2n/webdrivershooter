package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Resource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;

/**
 * Operate coordinates on screen.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public class Screener {
    private final double dpr;
    private final WebDriver driver;
    private final Rectangle innerRect;
    private final Rectangle outerRect;

    /**
     * Construct a {@link Screener} object.
     *
     * @param checkDPR Indicate to check device pixel ratio.
     * @param driver   The current {@link WebDriver}.
     */
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

    /**
     * Construct a {@link Screener} object.
     *
     * @param checkDPR Indicate to check device pixel ratio.
     * @param driver   The current {@link WebDriver}.
     * @param element  The element to operate.
     */
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

    /**
     * Get device pixel ratio.
     *
     * @param checkDPR Indicate to check device pixel ratio. It will return 1.0 if it's false.
     * @param driver   The current {@link WebDriver}.
     * @return The device pixel ratio.
     */
    public static double getDPR(boolean checkDPR, WebDriver driver) {
        if (!checkDPR) {
            return 1.0;
        } else {
            String resource = "com/github/ngoanh2n/wds/GetDevicePixelRatio.js";
            Object value = executeScript(driver, resource);
            return value instanceof Double ? (Double) value : (Long) value * 1.0;
        }
    }

    /**
     * Execute JavaScript in the context of the currently selected frame or window.
     *
     * @param driver   The current {@link WebDriver}.
     * @param resource The name of JavaScript as a resource file. <br>
     *                 e.g. com/foo/File.js
     * @param args     The arguments to the script. May be empty.
     * @return One of Boolean, Long, Double, String, List, Map or WebElement. Or null.
     */
    public static Object executeScript(WebDriver driver, String resource, Object... args) {
        String script = Resource.getContent(resource);
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get device pixel ratio.
     *
     * @return The device pixel ratio.
     */
    public double getDPR() {
        return dpr;
    }

    /**
     * Get the inner rectangle:
     * <ul>
     *     <li>Viewport if the current target is page</li>
     *     <li>Bounding rectangle if the current target is a element or frame</li>
     * </ul>
     *
     * @return The {@link Rectangle}.
     */
    public Rectangle getInnerRect() {
        return innerRect;
    }

    /**
     * Get the outer rectangle:
     * <ul>
     *     <li>Document if the current target is page</li>
     *     <li>Scrolled bounding rectangle if the current target is a element or frame</li>
     * </ul>
     *
     * @return The {@link Rectangle}.
     */
    public Rectangle getOuterRect() {
        return outerRect;
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get width of viewport.
     *
     * @return The width of viewport.
     */
    public int getViewportWidth() {
        String resource = "com/github/ngoanh2n/wds/GetViewportWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get height of viewport.
     *
     * @return The height of viewport.
     */
    public int getViewportHeight() {
        String resource = "com/github/ngoanh2n/wds/GetViewportHeight.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get width of document.
     *
     * @return The width of document.
     */
    public int getDocumentWidth() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get height of document.
     *
     * @return The height of document.
     */
    public int getDocumentHeight() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentHeight.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the current scroll left of document.
     *
     * @return The current scroll left.
     */
    public int getDocumentScrollX() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollX.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the current scroll top of document.
     *
     * @return The current scroll top.
     */
    public int getDocumentScrollY() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollY.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll bar width of document.
     *
     * @return The scroll bar.
     */
    public int getDocumentScrollBarWidth() {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollBarWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Scroll to the specified point.
     *
     * @param point The target point.
     */
    public void scrollToPoint(Point point) {
        double x = point.x / dpr;
        double y = point.y / dpr;
        String resource = "com/github/ngoanh2n/wds/ScrollToPoint.js";
        executeScript(driver, resource, x, y);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get left bounding of the element.
     *
     * @param element The current {@link WebElement}.
     * @return The left bounding.
     */
    public int getElementRectLeft(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectLeft.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get top bounding of the element.
     *
     * @param element The current {@link WebElement}.
     * @return The top bounding.
     */
    public int getElementRectTop(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectTop.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get width of bounding rectangle for element.
     *
     * @param element The current {@link WebElement}.
     * @return The width of bounding rectangle.
     */
    public int getElementRectWidth(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get height of bounding rectangle for element.
     *
     * @param element The current {@link WebElement}.
     * @return The height of bounding rectangle.
     */
    public int getElementRectHeight(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the number of pixels an element's content is scrolled vertically.
     *
     * @param element The current {@link WebElement}.
     * @return The number of pixels are scrolled vertically.
     */
    public int getElementScrollY(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollY.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the number of pixels an element's content is scrolled horizontally.
     *
     * @param element The current {@link WebElement}.
     * @return The number of pixels are scrolled horizontally.
     */
    public int getElementScrollX(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollX.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the width of element.
     *
     * @param element The current {@link WebElement}.
     * @return The width of element.
     */
    public int getElementScrollWidth(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the height of element.
     *
     * @param element The current {@link WebElement}.
     * @return The height of element.
     */
    public int getElementScrollHeight(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll bar width of element.
     *
     * @param element The current {@link WebElement}.
     * @return Scroll bar width of element.
     */
    public int getElementScrollBarWidth(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollBarWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll bar height of element.
     *
     * @param element The current {@link WebElement}.
     * @return Scroll bar height of element.
     */
    public int getElementScrollBarHeight(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollBarHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Scroll element into the top of the visible area.
     *
     * @param element The current {@link WebElement}.
     */
    public void scrollElementIntoView(WebElement element) {
        String resource = "com/github/ngoanh2n/wds/ScrollElementIntoView.js";
        executeScript(driver, resource, element);
    }

    /**
     * Scroll element to the specified point.
     *
     * @param element The current {@link WebElement}.
     * @param point   The target point.
     */
    public void scrollElementToPoint(WebElement element, Point point) {
        double x = point.x / dpr;
        double y = point.y / dpr;
        String resource = "com/github/ngoanh2n/wds/ScrollElementToPoint.js";
        executeScript(driver, resource, element, x, y);
    }
}
