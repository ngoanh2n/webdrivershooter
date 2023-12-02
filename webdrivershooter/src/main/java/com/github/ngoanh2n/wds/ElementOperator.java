package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Operate coordinates and rectangles on screen for element.<br><br>
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
public class ElementOperator extends ShooterOperator {
    /**
     * The element to be captured.
     */
    protected WebElement element;

    /**
     * Construct a new {@link ElementOperator}.
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
    protected boolean isImageFull(BufferedImage shot) {
        return switch (options.shooter()) {
            case 1 -> true;
            case 2 -> getShotImageHeight() == shot.getHeight(null);
            case 3 -> getShotImageWidth() == shot.getWidth(null);
            default -> getShotImageWidth() == shot.getWidth(null) && getShotImageHeight() == shot.getHeight(null);
        };
    }

    //-------------------------------------------------------------------------------//

    /**
     * Scroll down with distance of element bounding rectangle height.
     *
     * @param part The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollSY(int part) {
        int pointX = screener.getElementScrollX(element);
        int pointY = (int) (part * screener.getInnerRect().getHeight());
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    /**
     * Scroll down with distance of element bounding rectangle width.
     *
     * @param part The multiplier to calculate the X coordinate of the next point be to scrolled to.
     */
    protected void scrollXS(int part) {
        int pointX = (int) (part * screener.getInnerRect().getWidth());
        int pointY = screener.getElementScrollY(element);
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    /**
     * Scroll to the specified point.
     *
     * @param partX The multiplier to calculate the X coordinate of the next point be to scrolled to.
     * @param partY The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollXY(int partX, int partY) {
        int pointX = (int) (partX * screener.getInnerRect().getWidth());
        int pointY = (int) (partY * screener.getInnerRect().getHeight());
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    //-------------------------------------------------------------------------------//

    /**
     * Draw the specified shot over the current image of {@link Screenshot} with its top-left corner at (0,0).
     *
     * @param shot The specified shot to be drawn over the current {@link Screenshot}.
     */
    protected void mergeShot00(BufferedImage shot) {
        shot = getElementShot(shot);
        int x = 0;
        int y = 0;
        screenshot.mergePart(shot, x, y);
    }

    /**
     * Draw the specified shot over the current image of {@link Screenshot} with its top-left corner at (0,scrollTop).
     *
     * @param shot The specified shot to be drawn over the current {@link Screenshot}.
     */
    protected void mergeShot0S(BufferedImage shot) {
        shot = getElementShot(shot);
        int x = 0;
        int y = screener.getElementScrollY(element);
        screenshot.mergePart(shot, x, y);
    }

    /**
     * Draw the specified shot over the current image of {@link Screenshot} with its top-left corner at (scrollLeft,0).
     *
     * @param shot The specified shot to be drawn over the current {@link Screenshot}.
     */
    protected void mergeShotS0(BufferedImage shot) {
        shot = getElementShot(shot);
        int x = screener.getElementScrollX(element);
        int y = 0;
        screenshot.mergePart(shot, x, y);
    }

    /**
     * Draw the specified shot over the current image of {@link Screenshot} with its top-left corner at (scrollLeft,scrollTop).
     *
     * @param shot The specified shot to be drawn over the current {@link Screenshot}.
     */
    protected void mergeShotSS(BufferedImage shot) {
        shot = getElementShot(shot);
        int x = screener.getElementScrollX(element);
        int y = screener.getElementScrollY(element);
        screenshot.mergePart(shot, x, y);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Cut image defined by a specified rectangular element.
     *
     * @param shot The larger image contains element.
     * @return The element image.
     */
    protected BufferedImage getElementShot(BufferedImage shot) {
        int x = screener.getOuterRect().x;
        int y = screener.getOuterRect().y;
        int w = screener.getInnerRect().width;
        int h = screener.getInnerRect().height;

        if (options.shooter() == 1) {
            if (shot.getHeight() < screener.getInnerRect().getHeight()) {
                h = shot.getHeight() - screener.getInnerRect().y;
                screenshot.updateImage(w, h);
            }
        }
        screenshot.updatedRects(x, y);
        return shot.getSubimage(x, y, w, h);
    }
}
