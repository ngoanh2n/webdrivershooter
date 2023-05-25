package com.github.ngoanh2n.wds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Operate coordinates and rectangles on screen.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public abstract class ShooterOperator {
    protected ShooterOptions options;
    protected WebDriver driver;
    protected boolean checkDPR;
    protected Screener screener;
    protected Screenshot screenshot;

    /**
     * Construct a new {@link ShooterOperator}.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param elements The element to support {@link #createScreener(WebElement...)}.<br>
     *                 Only the first {@link WebElement} of element array is used. May be empty.
     */
    protected ShooterOperator(ShooterOptions options, WebDriver driver, WebElement... elements) {
        this.options = options;
        this.driver = driver;
        this.checkDPR = options.checkDPR();
        this.screener = createScreener(elements);
        this.screenshot = createScreenshot();
    }

    //-------------------------------------------------------------------------------//

    /**
     * Create a {@link Screener}.
     *
     * @param elements The element to support {@link Screener} when interacting a element.
     * @return The {@link Screener}.
     */
    protected Screener createScreener(WebElement... elements) {
        return new Screener(checkDPR, driver);
    }

    /**
     * Create a {@link Screenshot}.
     *
     * @return The {@link Screenshot}.
     */
    protected Screenshot createScreenshot() {
        int width = getImageWidth();
        int height = getImageHeight();
        List<WebElement> elements = getElements();
        List<Rectangle> rects = getRectangles(elements);
        boolean maskForRects = options.maskForElements();
        Color maskedColor = options.maskedColor();
        return new Screenshot(width, height, rects, maskForRects, maskedColor);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get elements to mask or ignore to be not masked.
     *
     * @return The {@link WebElement} list.
     */
    protected List<WebElement> getElements() {
        if (options.elements().size() > 0) {
            return options.elements();
        }
        return getElements(options.locators());
    }

    /**
     * Get elements to mask or ignore to be not masked.
     *
     * @param locators List of {@link By} to mask or ignore to be not masked.
     * @return The {@link By} list.
     */
    protected List<WebElement> getElements(List<By> locators) {
        List<WebElement> elements = new ArrayList<>();
        for (By locator : locators) {
            WebElement element = driver.findElement(locator);
            elements.add(element);
        }
        return elements;
    }

    /**
     * Get areas to mask or ignore to be not masked.
     *
     * @param elements The list of {@link WebElement} to mask or ignore to be not masked.
     * @return The {@link Rectangle} list.
     */
    protected List<Rectangle> getRectangles(List<WebElement> elements) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (WebElement element : elements) {
            int x = (int) (element.getLocation().getX() * screener.getDPR());
            int y = (int) (element.getLocation().getY() * screener.getDPR());
            int w = (int) (element.getSize().getWidth() * screener.getDPR());
            int h = (int) (element.getSize().getHeight() * screener.getDPR());

            Point location = new Point(x, y);
            Dimension size = new Dimension(w, h);
            rectangles.add(new Rectangle(location, size));
        }
        return rectangles;
    }

    /**
     * Get the width of image for creating the {@link Screenshot}.
     *
     * @return The width of image.
     */
    protected int getImageWidth() {
        switch (options.shooter()) {
            case 1:
            case 2:
                return (int) screener.getInnerRect().getWidth();
            default:
                return (int) screener.getOuterRect().getWidth();
        }
    }

    /**
     * Get the height of image for creating the {@link Screenshot}.
     *
     * @return The height of image.
     */
    protected int getImageHeight() {
        switch (options.shooter()) {
            case 1:
            case 3:
                return (int) screener.getInnerRect().getHeight();
            default:
                return (int) screener.getOuterRect().getHeight();
        }
    }

    /**
     * Whether to check the screenshot image is merged fully.
     *
     * @param part The current screenshot part.
     * @return Indicate the image is merged fully.
     */
    protected boolean isImageFull(BufferedImage part) {
        switch (options.shooter()) {
            case 1:
            case 2:
                return getImageHeight() == part.getHeight(null);
            case 3:
                return getImageWidth() == part.getWidth(null);
            default:
                return getImageWidth() == part.getWidth(null) && getImageHeight() == part.getHeight(null);
        }
    }

    //-------------------------------------------------------------------------------//

    /**
     * Make the current thread to sleep with {@link ShooterOptions#scrollDelay()} ms.
     */
    protected void sleep() {
        try {
            Thread.sleep(options.scrollDelay());
        } catch (InterruptedException e) {
            throw new ShooterException(e);
        }
    }

    /**
     * Get the number of times are scrolled vertically.
     *
     * @return The number of screenshot parts need to merge into the screenshot image by vertically.
     */
    protected int getPartsY() {
        int outerH = (int) screener.getOuterRect().getHeight();
        int innerH = (int) screener.getInnerRect().getHeight();
        return (int) Math.ceil(((double) outerH) / innerH);
    }

    /**
     * Get the number of times are scrolled horizontally.
     *
     * @return The number of screenshot parts need to merge into the screenshot image by horizontally.
     */
    protected int getPartsX() {
        int outerW = (int) screener.getOuterRect().getWidth();
        int innerW = (int) screener.getInnerRect().getWidth();
        return (int) Math.ceil(((double) outerW) / innerW);
    }

    /**
     * Get the current {@link Screenshot}.
     *
     * @return The {@link Screenshot}.
     */
    protected Screenshot getScreenshot() {
        return screenshot;
    }
}
