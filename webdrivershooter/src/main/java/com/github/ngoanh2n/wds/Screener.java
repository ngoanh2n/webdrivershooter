package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Resources;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Operate coordinates on screen.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/webdrivershooter">ngoanh2n/webdrivershooter</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter">com.github.ngoanh2n:webdrivershooter</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
@ParametersAreNonnullByDefault
public class Screener {
    private final static Logger log = LoggerFactory.getLogger(Screener.class);
    private final double dpr;
    private final WebDriver driver;
    private final int header;
    private final int footer;
    private final Dimension scrollbar;
    private final Rectangle outerRect;
    private final Rectangle innerRect;

    /**
     * Construct a new {@link Screener}.
     *
     * @param driver The current {@link WebDriver}.
     */
    public Screener(WebDriver driver) {
        this(driver, 0, 0);
    }

    /**
     * Construct a new {@link Screener}.
     *
     * @param driver The current {@link WebDriver}.
     * @param header The height of header is being fixed.
     * @param footer The height of footer is being fixed.
     */
    public Screener(WebDriver driver, int header, int footer) {
        this(driver, true, header, footer);
    }

    /**
     * Construct a new {@link Screener}.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     */
    public Screener(ShooterOptions options, WebDriver driver) {
        this(driver, options.checkDPR(), options.headerToBeFixed(), options.footerToBeFixed());
    }

    /**
     * Construct a new {@link Screener}.
     *
     * @param checkDPR Indicate to check device pixel ratio.
     * @param driver   The current {@link WebDriver}.
     */
    public Screener(WebDriver driver, boolean checkDPR, int header, int footer) {
        this.dpr = getDPR(driver, checkDPR);
        this.driver = driver;
        this.header = header;
        this.footer = footer;

        int sbW = getScrollbarWidth();
        int sbH = getScrollbarHeight();

        int outerX = 0;
        int outerY = 0;
        int outerW = getWidth();
        int outerH = getHeight();

        int innerX = getScrollX();
        int innerY = getScrollY();
        int innerW = getViewportWidth();
        int innerH = getViewportHeight();

        Point outerLocation = new Point(outerX, outerY);
        Point innerLocation = new Point(innerX, innerY);
        Dimension outerSize = new Dimension(outerW, outerH);
        Dimension innerSize = new Dimension(innerW, innerH);

        if (outerW < innerW) {
            outerSize.setWidth(innerW);
        }

        this.scrollbar = new Dimension(sbW, sbH);
        this.outerRect = new Rectangle(outerLocation, outerSize);
        this.innerRect = new Rectangle(innerLocation, innerSize);

        log.info("Outer [{}]", outerRect);
        log.info("Inner [{}]", innerRect);
        log.info("Y scrollbar [{}]", scrollbar.getWidth());
        log.info("X scrollbar [{}]", scrollbar.getHeight());
    }

    //-------------------------------------------------------------------------------//

    /**
     * Construct a new {@link Screener}.
     *
     * @param driver  The current {@link WebDriver}.
     * @param element The element to operate.
     */
    public Screener(WebDriver driver, WebElement element) {
        this(driver, element, 0, 0);
    }

    /**
     * Construct a new {@link Screener}.
     *
     * @param driver  The current {@link WebDriver}.
     * @param element The element to operate.
     * @param header  The height of header is being fixed.
     * @param footer  The height of footer is being fixed.
     */
    public Screener(WebDriver driver, WebElement element, int header, int footer) {
        this(driver, element, true, header, footer);
    }

    /**
     * Construct a new {@link Screener}.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @param element The element to operate.
     */
    public Screener(ShooterOptions options, WebDriver driver, WebElement element) {
        this(driver, element, options.checkDPR(), options.headerToBeFixed(), options.footerToBeFixed());
    }

    /**
     * Construct a new {@link Screener}.
     *
     * @param checkDPR Indicate to check device pixel ratio.
     * @param driver   The current {@link WebDriver}.
     * @param element  The element to operate.
     */
    public Screener(WebDriver driver, WebElement element, boolean checkDPR, int header, int footer) {
        this.dpr = getDPR(driver, checkDPR);
        this.driver = driver;
        this.header = header;
        this.footer = footer;

        boolean hasSbX = hasScrollbarX(element);
        boolean hasSbY = hasScrollbarY(element);

        int sbW = hasSbX ? getScrollbarWidth(element) : 0;
        int sbH = hasSbY ? getScrollbarHeight(element) : 0;

        int outerX = (int) (element.getLocation().x * dpr);
        int outerY = (int) (element.getLocation().y * dpr);
        int outerW = (int) (element.getSize().width * dpr);
        int outerH = (int) (element.getSize().height * dpr);

        if (hasSbX || hasSbY) {
            outerW = getScrollWidth(element) + sbW;
            outerH = getScrollHeight(element) + sbH;
        }

        int innerX = getRectLeft(element);
        int innerY = getRectTop(element);
        int innerW = getRectWidth(element);
        int innerH = getRectHeight(element);

        Point outerLocation = new Point(outerX, outerY);
        Point innerLocation = new Point(innerX, innerY);
        Dimension outerSize = new Dimension(outerW, outerH);
        Dimension innerSize = new Dimension(innerW, innerH);

        int viewH = getViewportHeight();
        if (viewH < innerH) {
            innerSize.setHeight(viewH);
        }

        this.scrollbar = new Dimension(sbW, sbH);
        this.innerRect = new Rectangle(innerLocation, innerSize);
        this.outerRect = new Rectangle(outerLocation, outerSize);

        log.info("Outer [{}]", outerRect);
        log.info("Inner [{}]", innerRect);
        log.info("Y scrollbar [{}]", scrollbar.getWidth());
        log.info("X scrollbar [{}]", scrollbar.getHeight());
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get width of viewport.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The width of viewport.
     */
    public static int getViewportWidth(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetViewportWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get height of viewport.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The height of viewport.
     */
    public static int getViewportHeight(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetViewportHeight.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get width of document.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The width of document.
     */
    public static int getWidth(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetDocumentWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get height of document.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The height of document.
     */
    public static int getHeight(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetDocumentHeight.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the current scroll left of document.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The current scroll left.
     */
    public static int getScrollX(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollX.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the current scroll top of document.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The current scroll top.
     */
    public static int getScrollY(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollY.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll bar width of document.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The width of scroll bar.
     */
    public static int getScrollbarWidth(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollbarWidth.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll bar height of document.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @return The height of scroll bar.
     */
    public static int getScrollbarHeight(WebDriver driver, double dpr) {
        String resource = "com/github/ngoanh2n/wds/GetDocumentScrollbarHeight.js";
        Object value = executeScript(driver, resource);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Scroll to the specified position.
     *
     * @param driver The current {@link WebDriver}.
     * @param dpr    Device pixel ratio.
     * @param point  The target position.
     */
    public static void scrollToPoint(Point point, WebDriver driver, double dpr) {
        double x = point.getX() / dpr;
        double y = point.getY() / dpr;
        String resource = "com/github/ngoanh2n/wds/ScrollToPoint.js";
        executeScript(driver, resource, x, y);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get top bounding of the element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The top bounding.
     */
    public static int getRectTop(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectTop.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get left bounding of the element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The left bounding.
     */
    public static int getRectLeft(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectLeft.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get width of bounding rectangle for element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The width of bounding rectangle.
     */
    public static int getRectWidth(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get height of bounding rectangle for element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The height of bounding rectangle.
     */
    public static int getRectHeight(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementRectHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll top of element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The scroll top of element.
     */
    public static int getScrollTop(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollTop.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll left of element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The scroll left of element.
     */
    public static int getScrollLeft(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollLeft.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the number of pixels an element's content is scrolled vertically.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The number of pixels are scrolled vertically.
     */
    public static int getScrollY(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollY.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the number of pixels an element's content is scrolled horizontally.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The number of pixels are scrolled horizontally.
     */
    public static int getScrollX(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollX.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the width of element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The width of element.
     */
    public static int getScrollWidth(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get the height of element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return The height of element.
     */
    public static int getScrollHeight(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll bar width of element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return Scroll bar width of element.
     */
    public static int getScrollbarWidth(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollbarWidth.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Get scroll bar height of element.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @return Scroll bar height of element.
     */
    public static int getScrollbarHeight(WebDriver driver, double dpr, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/GetElementScrollbarHeight.js";
        Object value = executeScript(driver, resource, element);
        return (int) (Double.parseDouble(value.toString()) * dpr);
    }

    /**
     * Scroll element into the top of the visible area.
     *
     * @param driver  The current {@link WebDriver}.
     * @param element The current {@link WebElement}.
     */
    public static void scrollIntoView(WebDriver driver, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/ScrollElementIntoView.js";
        executeScript(driver, resource, element);
    }

    /**
     * Scroll element to the specified position.
     *
     * @param driver  The current {@link WebDriver}.
     * @param dpr     Device pixel ratio.
     * @param element The current {@link WebElement}.
     * @param point   The target position.
     */
    public static void scrollToPoint(WebDriver driver, double dpr, WebElement element, Point point) {
        double x = point.getX() / dpr;
        double y = point.getY() / dpr;
        String resource = "com/github/ngoanh2n/wds/ScrollElementToPoint.js";
        executeScript(driver, resource, element, x, y);
    }

    /**
     * Check whether {@code WebElement} has vertical scrollbar.
     *
     * @param driver  The current {@link WebDriver}.
     * @param element The current {@link WebElement}.
     * @return {@code true} if {@code WebElement} has vertical scrollbar; {@code false} otherwise.
     */
    public static boolean hasScrollbarY(WebDriver driver, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/ElementHasScrollbarY.js";
        Object value = executeScript(driver, resource, element);
        return Boolean.parseBoolean(value.toString());
    }

    /**
     * Check whether {@code WebElement} has horizontal scrollbar.
     *
     * @param driver  The current {@link WebDriver}.
     * @param element The current {@link WebElement}.
     * @return {@code true} if {@code WebElement} has horizontal scrollbar; {@code false} otherwise.
     */
    public static boolean hasScrollbarX(WebDriver driver, WebElement element) {
        String resource = "com/github/ngoanh2n/wds/ElementHasScrollbarX.js";
        Object value = executeScript(driver, resource, element);
        return Boolean.parseBoolean(value.toString());
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get device pixel ratio.
     *
     * @param driver The current {@link WebDriver}.
     * @return The device pixel ratio.
     */
    public static double getDPR(WebDriver driver) {
        String resource = "com/github/ngoanh2n/wds/GetDevicePixelRatio.js";
        Object value = executeScript(driver, resource);
        return value instanceof Double ? (Double) value : (Long) value * 1.0;
    }

    /**
     * Execute JavaScript in the context of the currently selected frame or window.
     *
     * @param driver   The current {@link WebDriver}.
     * @param resource The name of JavaScript as a resource file.<br>
     *                 e.g. com/foo/File.js
     * @param args     The arguments to the script. May be empty.
     * @return One of Boolean, Long, Double, String, List, Map or WebElement. Or null.
     */
    public static Object executeScript(WebDriver driver, String resource, Object... args) {
        String script = Resources.getContent(resource);
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    /**
     * Get device pixel ratio.
     *
     * @param checkDPR Indicate to check device pixel ratio. It will return 1.0 if it's false.
     * @param driver   The current {@link WebDriver}.
     * @return The device pixel ratio.
     */
    private static double getDPR(WebDriver driver, boolean checkDPR) {
        if (!checkDPR) {
            return 1.0;
        } else {
            return getDPR(driver);
        }
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
     * Get the height of header.
     *
     * @return The height of header.
     */
    public int getHeader() {
        return (int) (header * dpr);
    }

    /**
     * Get the height of footer.
     *
     * @return The height of footer.
     */
    public int getFooter() {
        return (int) (footer * dpr);
    }

    /**
     * Get the width of vertical scrollbar and height of horizontal scrollbar.
     *
     * @return The width of vertical scrollbar and height of horizontal scrollbar.
     */
    public Dimension getScrollbar() {
        return scrollbar;
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
        return getViewportWidth(driver, dpr);
    }

    /**
     * Get height of viewport.
     *
     * @return The height of viewport.
     */
    public int getViewportHeight() {
        return getViewportHeight(driver, dpr);
    }

    /**
     * Get width of document.
     *
     * @return The width of document.
     */
    public int getWidth() {
        return getWidth(driver, dpr);
    }

    /**
     * Get height of document.
     *
     * @return The height of document.
     */
    public int getHeight() {
        return getHeight(driver, dpr);
    }

    /**
     * Get the current scroll left of document.
     *
     * @return The current scroll left.
     */
    public int getScrollX() {
        return getScrollX(driver, dpr);
    }

    /**
     * Get the current scroll top of document.
     *
     * @return The current scroll top.
     */
    public int getScrollY() {
        return getScrollY(driver, dpr);
    }

    /**
     * Get scroll bar width of document.
     *
     * @return The width of scroll bar.
     */
    public int getScrollbarWidth() {
        return getScrollbarWidth(driver, dpr);
    }

    /**
     * Get scroll bar height of document.
     *
     * @return The height of scroll bar.
     */
    public int getScrollbarHeight() {
        return getScrollbarHeight(driver, dpr);
    }

    /**
     * Scroll to the specified position.
     *
     * @param point The target position.
     */
    public void scrollToPoint(Point point) {
        scrollToPoint(point, driver, dpr);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get top bounding of the element.
     *
     * @param element The current {@link WebElement}.
     * @return The top bounding.
     */
    public int getRectTop(WebElement element) {
        return getRectTop(driver, dpr, element);
    }

    /**
     * Get left bounding of the element.
     *
     * @param element The current {@link WebElement}.
     * @return The left bounding.
     */
    public int getRectLeft(WebElement element) {
        return getRectLeft(driver, dpr, element);
    }

    /**
     * Get width of bounding rectangle for element.
     *
     * @param element The current {@link WebElement}.
     * @return The width of bounding rectangle.
     */
    public int getRectWidth(WebElement element) {
        return getRectWidth(driver, dpr, element);
    }

    /**
     * Get height of bounding rectangle for element.
     *
     * @param element The current {@link WebElement}.
     * @return The height of bounding rectangle.
     */
    public int getRectHeight(WebElement element) {
        return getRectHeight(driver, dpr, element);
    }

    /**
     * Get scroll top of element.
     *
     * @param element The current {@link WebElement}.
     * @return The scroll top of element.
     */
    public int getScrollTop(WebElement element) {
        return getScrollTop(driver, dpr, element);
    }

    /**
     * Get scroll left of element.
     *
     * @param element The current {@link WebElement}.
     * @return The scroll left of element.
     */
    public int getScrollLeft(WebElement element) {
        return getScrollLeft(driver, dpr, element);
    }

    /**
     * Get the number of pixels an element's content is scrolled vertically.
     *
     * @param element The current {@link WebElement}.
     * @return The number of pixels are scrolled vertically.
     */
    public int getScrollY(WebElement element) {
        return getScrollY(driver, dpr, element);
    }

    /**
     * Get the number of pixels an element's content is scrolled horizontally.
     *
     * @param element The current {@link WebElement}.
     * @return The number of pixels are scrolled horizontally.
     */
    public int getScrollX(WebElement element) {
        return getScrollX(driver, dpr, element);
    }

    /**
     * Get the width of element.
     *
     * @param element The current {@link WebElement}.
     * @return The width of element.
     */
    public int getScrollWidth(WebElement element) {
        return getScrollWidth(driver, dpr, element);
    }

    /**
     * Get the height of element.
     *
     * @param element The current {@link WebElement}.
     * @return The height of element.
     */
    public int getScrollHeight(WebElement element) {
        return getScrollHeight(driver, dpr, element);
    }

    /**
     * Get scroll bar width of element.
     *
     * @param element The current {@link WebElement}.
     * @return Scroll bar width of element.
     */
    public int getScrollbarWidth(WebElement element) {
        return getScrollbarWidth(driver, dpr, element);
    }

    /**
     * Get scroll bar height of element.
     *
     * @param element The current {@link WebElement}.
     * @return Scroll bar height of element.
     */
    public int getScrollbarHeight(WebElement element) {
        return getScrollbarHeight(driver, dpr, element);
    }

    /**
     * Scroll element into the top of the visible area.
     *
     * @param element The current {@link WebElement}.
     */
    public void scrollIntoView(WebElement element) {
        scrollIntoView(driver, element);
    }

    /**
     * Scroll element to the specified position.
     *
     * @param element The current {@link WebElement}.
     * @param point   The target position.
     */
    public void scrollToPoint(WebElement element, Point point) {
        scrollToPoint(driver, dpr, element, point);
    }

    /**
     * Check whether {@code WebElement} has vertical scrollbar.
     *
     * @param element The current {@link WebElement}.
     * @return {@code true} if {@code WebElement} has vertical scrollbar; {@code false} otherwise.
     */
    public boolean hasScrollbarY(WebElement element) {
        return hasScrollbarY(driver, element);
    }

    /**
     * Check whether {@code WebElement} has horizontal scrollbar.
     *
     * @param element The current {@link WebElement}.
     * @return {@code true} if {@code WebElement} has horizontal scrollbar; {@code false} otherwise.
     */
    public boolean hasScrollbarX(WebElement element) {
        return hasScrollbarX(driver, element);
    }
}
