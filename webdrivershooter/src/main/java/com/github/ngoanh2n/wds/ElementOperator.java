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

    @Override
    protected Screener screener(WebElement... elements) {
        WebElement element = elements[0];
        return Screener.element(checkDPR, driver, element);
    }

    @Override
    protected int imageWidth() {
        switch (options.shooterStrategy()) {
            case 1:
            case 2:
                return (int) screener.getInnerRect().getWidth();
            default:
                return (int) screener.getOuterRect().getWidth();
        }
    }

    @Override
    protected int imageHeight() {
        switch (options.shooterStrategy()) {
            case 1:
            case 3:
                return (int) screener.getInnerRect().getHeight();
            default:
                return (int) screener.getOuterRect().getHeight();
        }
    }

    @Override
    protected boolean imageFull(BufferedImage part) {
        switch (options.shooterStrategy()) {
            case 1:
                return true;
            case 2:
                return imageHeight() == part.getHeight(null);
            case 3:
                return imageWidth() == part.getWidth(null);
            default:
                return imageWidth() == part.getWidth(null) && imageHeight() == part.getHeight(null);
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
        getGraphics().drawImage(part, x, y, null);
    }

    protected void mergePart0S(BufferedImage part) {
        part = getElementPart(part);
        int x = 0;
        int y = screener.getElementScrollY(element);
        getGraphics().drawImage(part, x, y, null);
    }

    protected void mergePartS0(BufferedImage part) {
        part = getElementPart(part);
        int x = screener.getElementScrollX(element);
        int y = 0;
        getGraphics().drawImage(part, x, y, null);
    }

    protected void mergePartSS(BufferedImage part) {
        part = getElementPart(part);
        int x = screener.getElementScrollX(element);
        int y = screener.getElementScrollY(element);
        getGraphics().drawImage(part, x, y, null);
    }

    protected BufferedImage getElementPart(BufferedImage part) {
        int x = (int) screener.getOuterRect().getX();
        int y = (int) screener.getOuterRect().getY();
        int w = (int) screener.getInnerRect().getWidth();
        int h = (int) screener.getInnerRect().getHeight();
        return part.getSubimage(x, y, w, h);
    }
}
