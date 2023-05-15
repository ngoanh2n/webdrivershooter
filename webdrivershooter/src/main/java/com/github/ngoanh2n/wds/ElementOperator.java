package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public class ElementOperator extends ShooterOperator {
    protected WebElement element;

    protected ElementOperator(ShooterOptions options, WebDriver driver, WebElement element) {
        super(options, driver, element);
        this.element = element;
        this.screener.scrollElementIntoView(element);
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected Screener createScreener(WebElement... elements) {
        WebElement element = elements[0];
        return Screener.element(checkDPR, driver, element);
    }

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

    protected void scrollSY(int multiplierY) {
        int pointX = screener.getElementScrollX(element);
        int pointY = (int) (multiplierY * screener.getInnerRect().getHeight());
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    protected void scrollXS(int multiplierX) {
        int pointX = (int) (multiplierX * screener.getInnerRect().getWidth());
        int pointY = screener.getElementScrollY(element);
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    protected void scrollXY(int multiplierX, int multiplierY) {
        int pointX = (int) (multiplierX * screener.getInnerRect().getWidth());
        int pointY = (int) (multiplierY * screener.getInnerRect().getHeight());
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    protected void mergePart00(BufferedImage part) {
        part = getElementPart(part);
        int x = 0;
        int y = 0;
        screenshot.mergePart(part, x, y);
    }

    protected void mergePart0S(BufferedImage part) {
        part = getElementPart(part);
        int x = 0;
        int y = screener.getElementScrollY(element);
        screenshot.mergePart(part, x, y);
    }

    protected void mergePartS0(BufferedImage part) {
        part = getElementPart(part);
        int x = screener.getElementScrollX(element);
        int y = 0;
        screenshot.mergePart(part, x, y);
    }

    protected void mergePartSS(BufferedImage part) {
        part = getElementPart(part);
        int x = screener.getElementScrollX(element);
        int y = screener.getElementScrollY(element);
        screenshot.mergePart(part, x, y);
    }

    protected BufferedImage getElementPart(BufferedImage part) {
        int x = (int) screener.getOuterRect().getX();
        int y = (int) screener.getOuterRect().getY();
        int w = (int) screener.getInnerRect().getWidth();
        int h = (int) screener.getInnerRect().getHeight();
        screenshot.updatedRectangles(x, y);
        return part.getSubimage(x, y, w, h);
    }
}
