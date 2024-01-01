package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Dimension;
import com.github.ngoanh2n.Point;
import com.github.ngoanh2n.Rectangle;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Operate coordinates and rectangles on screen.<br><br>
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
public abstract class ShooterOperator {
    /**
     * {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}
     */
    protected ShooterOptions options;
    /**
     * The current {@link WebDriver}.
     */
    protected WebDriver driver;
    /**
     * Operate coordinates on screen.
     */
    protected Screener screener;
    /**
     * Merge shot parts and mask rectangles.
     */
    protected ShotImage shotImage;

    /**
     * Construct a new {@link ShooterOperator}.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @param targets The element to support {@link #createScreener(WebElement...)}.<br>
     *                Only the first {@link WebElement} of element array is used. May be empty.
     */
    protected ShooterOperator(ShooterOptions options, WebDriver driver, WebElement... targets) {
        this.options = options;
        this.driver = driver;
        this.screener = createScreener(targets);
        this.shotImage = createShotImage(options);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Create a {@link Screener}.
     *
     * @param targets The element to support {@link Screener} when interacting a element.
     * @return The {@link Screener}.
     */
    protected Screener createScreener(WebElement... targets) {
        return new Screener(options, driver);
    }

    /**
     * Create a {@link ShotImage}.
     *
     * @return The {@link ShotImage}.
     */
    protected ShotImage createShotImage(ShooterOptions options) {
        int w = getShotImageWidth();
        int h = getShotImageHeight();
        Dimension size = new Dimension(w, h);
        List<WebElement> elements = getElements();
        List<Rectangle> areaRects = getAreaRects(elements);
        return new ShotImage(options, screener, areaRects, size);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get elements to mask or ignore to be not masked.
     *
     * @return The {@link WebElement} list.
     */
    protected List<WebElement> getElements() {
        if (!options.elements().isEmpty()) {
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
    protected List<Rectangle> getAreaRects(List<WebElement> elements) {
        List<Rectangle> rects = new ArrayList<>();
        for (WebElement element : elements) {
            int x = (int) (screener.getDPR() * element.getLocation().getX());
            int y = (int) (screener.getDPR() * element.getLocation().getY());
            int w = (int) (screener.getDPR() * element.getSize().getWidth());
            int h = (int) (screener.getDPR() * element.getSize().getHeight());

            Point location = new Point(x, y);
            Dimension size = new Dimension(w, h);
            rects.add(new Rectangle(location, size));
        }
        return rects;
    }

    /**
     * Get the width of image for creating the {@link Screenshot}.
     *
     * @return The width of image.
     */
    protected int getShotImageWidth() {
        return switch (options.shooter()) {
            case 1, 2 -> screener.getInnerRect().getWidth();
            default -> screener.getOuterRect().getWidth();
        };
    }

    /**
     * Get the height of image for creating the {@link Screenshot}.
     *
     * @return The height of image.
     */
    protected int getShotImageHeight() {
        return switch (options.shooter()) {
            case 1, 3 -> screener.getInnerRect().getHeight();
            default -> screener.getOuterRect().getHeight();
        };
    }

    //-------------------------------------------------------------------------------//

    /**
     * Scroll to the specified point.
     *
     * @param partX The multiplier to calculate the X coordinate of the next point be to scrolled to.
     * @param partY The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected Shot.Position scrollTo(int partX, int partY) {
        Point location = new Point();
        Rectangle innerRect = screener.getInnerRect();

        switch (options.shooter()) {
            case 2:
                int header = screener.getHeader();
                location.setX(screener.getScrollX());
                location.setY((innerRect.getHeight() - header) * partY);
                break;
            case 3:
                location.setX(innerRect.getWidth() * partX);
                location.setY(screener.getScrollY());
                break;
            case 4:
                location.setX(innerRect.getWidth() * partX);
                location.setY(innerRect.getHeight() * partY);
                break;
        }

        screener.scrollToPoint(location);
        return getShotPosition(partX, partY);
    }

    protected Shot.Position getShotPosition(int partX, int partY) {
        int lastX = getPartsX() - 1;
        int lastY = getPartsY() - 1;

        Shot.Position.Type x = Shot.Position.Type.U;
        Shot.Position.Type y = Shot.Position.Type.U;

        if (partX == 0) {
            x = Shot.Position.Type.S;
        } else if (partX < lastX) {
            x = Shot.Position.Type.M;
        } else if (partX == lastX) {
            x = Shot.Position.Type.L;
        }
        if (partY == 0) {
            y = Shot.Position.Type.S;
        } else if (partY < lastY) {
            y = Shot.Position.Type.M;
        } else if (partY == lastY) {
            y = Shot.Position.Type.L;
        }
        return new Shot.Position(x, y);
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (currentScrollX,currentScrollY).
     *
     * @param shot The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergeShot(Shot shot) {
        shotImage.merge(shot);
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
     * Get device pixel ratio.
     *
     * @return The device pixel ratio.
     */
    protected double getDPR() {
        return screener.getDPR();
    }

    /**
     * Get the number of times are scrolled vertically.
     *
     * @return The number of screenshot parts need to merge into the screenshot image by vertically.
     */
    protected int getPartsY() {
        int outerH = screener.getOuterRect().getHeight();
        int innerH = screener.getInnerRect().getHeight();
        return (int) Math.ceil(((double) outerH) / innerH);
    }

    /**
     * Get the number of times are scrolled horizontally.
     *
     * @return The number of screenshot parts need to merge into the screenshot image by horizontally.
     */
    protected int getPartsX() {
        int outerW = screener.getOuterRect().getWidth();
        int innerW = screener.getInnerRect().getWidth();
        return (int) Math.ceil(((double) outerW) / innerW);
    }

    /**
     * Get the result after {@link WebDriverShooter#shoot(WebDriverShooter, ShooterOptions, WebDriver...)}.
     *
     * @return The {@link Screenshot}.
     */
    protected Screenshot getScreenshot() {
        ImmutablePair<Shot, List<Rectangle>> result = shotImage.getResult();
        Rectangle rect = result.getLeft().getRect();
        BufferedImage image = result.getLeft().getImage();
        List<Rectangle> areaRects = result.getRight();
        boolean maskArea = options.maskForAreas();
        Color maskedColor = options.maskedColor();
        return new Screenshot(rect, image, areaRects, maskArea, maskedColor);
    }
}
