package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Operate coordinates and rectangles on screen for page.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public class PageOperator extends ShooterOperator {
    /**
     * Construct a {@link PageOperator} object.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param elements The element to support {@link #createScreener(WebElement...)}.<br>
     *                 But it will be ignored for this case.
     */
    protected PageOperator(ShooterOptions options, WebDriver driver, WebElement... elements) {
        super(options, driver, elements);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Scroll down with distance of a viewport height.
     *
     * @param multiplierY The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollSY(int multiplierY) {
        int pointX = screener.getDocumentScrollX();
        int pointY = (int) screener.getInnerRect().getHeight() * multiplierY;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    /**
     * Scroll to the right with distance of a viewport width.
     *
     * @param multiplierX The multiplier to calculate the X coordinate of the next point be to scrolled to.
     */
    protected void scrollXS(int multiplierX) {
        int pointX = (int) screener.getInnerRect().getWidth() * multiplierX;
        int pointY = screener.getDocumentScrollY();
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    /**
     * Scroll to the specified point.
     *
     * @param multiplierX The multiplier to calculate the X coordinate of the next point be to scrolled to.
     * @param multiplierY The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollXY(int multiplierX, int multiplierY) {
        int pointX = (int) screener.getInnerRect().getWidth() * multiplierX;
        int pointY = (int) screener.getInnerRect().getHeight() * multiplierY;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (0,y).
     *
     * @param part        The specified part to be drawn over the current {@link Screenshot}.
     * @param multiplierY The multiplier to calculate the Y coordinate of the current {@link Screenshot}.
     */
    protected void mergePart0Y(BufferedImage part, int multiplierY) {
        int x = 0;
        int y = (int) screener.getInnerRect().getHeight() * multiplierY;
        screenshot.mergePart(part, x, y);
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (x,0).
     *
     * @param part        The specified part to be drawn over the current {@link Screenshot}.
     * @param multiplierX The multiplier to calculate the X coordinate of the current {@link Screenshot}.
     */
    protected void mergePartX0(BufferedImage part, int multiplierX) {
        int x = (int) screener.getInnerRect().getWidth() * multiplierX;
        int y = 0;
        screenshot.mergePart(part, x, y);
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (currentScrollX,currentScrollY).
     *
     * @param part The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergePartSS(BufferedImage part) {
        int x = screener.getDocumentScrollX();
        int y = screener.getDocumentScrollY();
        screenshot.mergePart(part, x, y);
    }
}
