package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
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
    private final WebElement target;

    /**
     * Construct a new {@link ElementOperator}.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @param target  The element to support {@link #createScreener(WebElement...)}.<br>
     */
    protected ElementOperator(ShooterOptions options, WebDriver driver, WebElement target) {
        super(options, driver, target);
        this.target = target;
    }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected Screener createScreener(WebElement... targets) {
        WebElement target = targets[0];
        return new Screener(options, driver, target);
    }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected Shot.Position scrollTo(int partX, int partY) {
        Point location = new Point();
        Shot.Position position = getShotPosition(partX, partY);

        if (position.isOrigin()) {
            screener.scrollIntoView(target);
        }

        Dimension scrollbar = screener.getScrollbar();
        Rectangle outerRect = screener.getOuterRect();
        Rectangle innerRect = screener.getInnerRect();

        switch (options.shooter()) {
            case 2:
                location.setX(screener.getScrollX(target));
                location.setY((innerRect.getHeight() - scrollbar.getHeight()) * partY);

                if (!position.isOrigin()) {
                    if (!screener.hasScrollbarY(target)) {
                        location.incY(outerRect.getY());
                        screener.scrollToPoint(location);
                    } else {
                        screener.scrollToPoint(target, location);
                    }
                }
                break;
            case 3:
                location.setX((innerRect.getWidth() - scrollbar.getWidth()) * partX);
                location.setY(screener.getScrollY(target));

                if (!position.isOrigin()) {
                    if (!screener.hasScrollbarX(target)) {
                        location.incX(outerRect.getX());
                        screener.scrollToPoint(location);
                    } else {
                        screener.scrollToPoint(target, location);
                    }
                }
                break;
            case 4:
                location.setX((innerRect.getWidth() - scrollbar.getWidth()) * partX);
                location.setY((innerRect.getHeight() - scrollbar.getHeight()) * partY);

                if (!position.isOrigin()) {
                    if (!screener.hasScrollbarX(target) || !screener.hasScrollbarY(target)) {
                        if (!screener.hasScrollbarX(target)) {
                            location.incX(outerRect.getX());
                        }
                        if (!screener.hasScrollbarY(target)) {
                            location.incY(outerRect.getY());
                        }
                        screener.scrollToPoint(location);
                    } else {
                        screener.scrollToPoint(target, location);
                    }
                }
                break;
        }
        return position;
    }

    /**
     * {@inheritDoc}
     */
    protected void mergeShot(Shot shot) {
        solveElementShot(shot);
        super.mergeShot(shot);
    }

    /**
     * Cut image defined by a specified rectangular element.
     *
     * @param shot The larger image contains element.
     */
    protected void solveElementShot(Shot shot) {
        BufferedImage image = shot.getImage();
        Rectangle eRect = getElementRect();
        BufferedImage eImage = ImageUtils.crop(image, eRect);

        shot.setImage(eImage);
        shot.getRect().setSize(eRect.getSize());

        Rectangle outerRect = screener.getOuterRect();
        shot.getRect().getLocation().setX(outerRect.getX());
        shot.getRect().getLocation().setY(outerRect.getY());

        if (screener.hasScrollbarY(target)) {
            int scrollTop = screener.getScrollTop(target);
            shot.getRect().getLocation().incY(scrollTop);
        } else {
            int rectTop = screener.getRectTop(target);
            if (rectTop < 0) {
                shot.getRect().getLocation().incY(Math.abs(rectTop));
            }
        }
        if (screener.hasScrollbarX(target)) {
            int scrollLeft = screener.getScrollLeft(target);
            shot.getRect().getLocation().incX(scrollLeft);
        } else {
            int rectLeft = screener.getRectLeft(target);
            if (rectLeft < 0) {
                shot.getRect().getLocation().incX(Math.abs(rectLeft));
            }
        }
    }

    protected Rectangle getElementRect() {
        int x = screener.getRectLeft(target);
        int y = screener.getRectTop(target);
        int w = screener.getInnerRect().getWidth();
        int h = screener.getInnerRect().getHeight();

        x = Math.max(0, x);
        y = Math.max(0, y);

        Point location = new Point(x, y);
        Dimension size = new Dimension(w, h);
        return new Rectangle(location, size);
    }
}
