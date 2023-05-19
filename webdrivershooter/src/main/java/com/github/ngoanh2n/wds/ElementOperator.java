package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Operate coordinates and rectangles on screen for element.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public class ElementOperator extends ShooterOperator {
    protected WebElement element;

    /**
     * Construct a {@link ElementOperator} object.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @param element The element to support {@link #createScreener(WebElement...)}.<br>
     */
    protected ElementOperator(ShooterOptions options, WebDriver driver, WebElement element) {
        super(options, driver, element);
        this.element = element;
        this.screener.scrollElementIntoView(element);
    }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected Screener createScreener(WebElement... elements) {
        WebElement element = elements[0];
        return new Screener(checkDPR, driver, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isImageFull(BufferedImage part) {
        switch (options.shooter()) {
            case 1:
                return true;
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
     * Scroll down with distance of element bounding rectangle height.
     *
     * @param multiplierY The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollSY(int multiplierY) {
        int pointX = screener.getElementScrollX(element);
        int pointY = (int) (multiplierY * screener.getInnerRect().getHeight());
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    /**
     * Scroll down with distance of element bounding rectangle width.
     *
     * @param multiplierX The multiplier to calculate the X coordinate of the next point be to scrolled to.
     */
    protected void scrollXS(int multiplierX) {
        int pointX = (int) (multiplierX * screener.getInnerRect().getWidth());
        int pointY = screener.getElementScrollY(element);
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    /**
     * Scroll to the specified point.
     *
     * @param multiplierX The multiplier to calculate the X coordinate of the next point be to scrolled to.
     * @param multiplierY The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollXY(int multiplierX, int multiplierY) {
        int pointX = (int) (multiplierX * screener.getInnerRect().getWidth());
        int pointY = (int) (multiplierY * screener.getInnerRect().getHeight());
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (0,0).
     *
     * @param part The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergePart00(BufferedImage part) {
        part = getElementImage(part);
        int x = 0;
        int y = 0;
        screenshot.mergePart(part, x, y);
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (0,scrollTop).
     *
     * @param part The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergePart0S(BufferedImage part) {
        part = getElementImage(part);
        int x = 0;
        int y = screener.getElementScrollY(element);
        screenshot.mergePart(part, x, y);
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (scrollLeft,0).
     *
     * @param part The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergePartS0(BufferedImage part) {
        part = getElementImage(part);
        int x = screener.getElementScrollX(element);
        int y = 0;
        screenshot.mergePart(part, x, y);
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (scrollLeft,scrollTop).
     *
     * @param part The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergePartSS(BufferedImage part) {
        part = getElementImage(part);
        int x = screener.getElementScrollX(element);
        int y = screener.getElementScrollY(element);
        screenshot.mergePart(part, x, y);
    }

    /**
     * Cut image defined by a specified rectangular element.
     *
     * @param outerImage The larger image contains element.
     * @return The element image.
     */
    protected BufferedImage getElementImage(BufferedImage outerImage) {
        int x = (int) screener.getOuterRect().getX();
        int y = (int) screener.getOuterRect().getY();
        int w = (int) screener.getInnerRect().getWidth();
        int h = (int) screener.getInnerRect().getHeight();
        screenshot.updatedRects(x, y);
        return outerImage.getSubimage(x, y, w, h);
    }
}
