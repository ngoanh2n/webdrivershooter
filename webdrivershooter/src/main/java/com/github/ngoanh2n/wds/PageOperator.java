package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Operate coordinates and rectangles on screen for page.<br><br>
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
public class PageOperator extends ShooterOperator {
    /**
     * Construct a new {@link PageOperator}.
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
     * @param part The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollSY(int part) {
        int pointX = screener.getDocumentScrollX();
        int pointY = (int) screener.getInnerRect().getHeight() * part;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    /**
     * Scroll to the right with distance of a viewport width.
     *
     * @param part The multiplier to calculate the X coordinate of the next point be to scrolled to.
     */
    protected void scrollXS(int part) {
        int pointX = (int) screener.getInnerRect().getWidth() * part;
        int pointY = screener.getDocumentScrollY();
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    /**
     * Scroll to the specified point.
     *
     * @param partX The multiplier to calculate the X coordinate of the next point be to scrolled to.
     * @param partY The multiplier to calculate the Y coordinate of the next point be to scrolled to.
     */
    protected void scrollXY(int partX, int partY) {
        int pointX = (int) screener.getInnerRect().getWidth() * partX;
        int pointY = (int) screener.getInnerRect().getHeight() * partY;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    //-------------------------------------------------------------------------------//

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (0,y).
     *
     * @param shot The specified part to be drawn over the current {@link Screenshot}.
     * @param part The multiplier to calculate the Y coordinate of the current {@link Screenshot}.
     */
    protected void mergeShot0Y(BufferedImage shot, int part) {
        int x = 0;
        int y = (int) screener.getInnerRect().getHeight() * part;
        screenshot.mergePart(shot, x, y);
    }

    /**
     * Draw the specified shot over the current image of {@link Screenshot} with its top-left corner at (x,0).
     *
     * @param shot The specified shot to be drawn over the current {@link Screenshot}.
     * @param part The multiplier to calculate the X coordinate of the current {@link Screenshot}.
     */
    protected void mergeShotX0(BufferedImage shot, int part) {
        int x = (int) screener.getInnerRect().getWidth() * part;
        int y = 0;
        screenshot.mergePart(shot, x, y);
    }

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (currentScrollX,currentScrollY).
     *
     * @param shot The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergeShotSS(BufferedImage shot) {
        int x = screener.getDocumentScrollX();
        int y = screener.getDocumentScrollY();
        screenshot.mergePart(shot, x, y);
    }
}
