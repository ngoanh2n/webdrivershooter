package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public class PageOperator extends ShooterOperator {
    protected PageOperator(ShooterOptions options, WebDriver driver, WebElement... elements) {
        super(options, driver, elements);
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected Screener screener(WebElement... elements) {
        return Screener.page(checkDPR, driver);
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
        int pointX = screener.getDocumentScrollX();
        int pointY = (int) screener.getInnerRect().getHeight() * multiplierY;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    protected void scrollXS(int multiplierX) {
        int pointX = (int) screener.getInnerRect().getWidth() * multiplierX;
        int pointY = screener.getDocumentScrollY();
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    protected void scrollXY(int multiplierX, int multiplierY) {
        int pointX = (int) screener.getInnerRect().getWidth() * multiplierX;
        int pointY = (int) screener.getInnerRect().getHeight() * multiplierY;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    protected void mergePart0Y(BufferedImage part, int multiplierY) {
        int x = 0;
        int y = (int) screener.getInnerRect().getHeight() * multiplierY;
        getGraphics().drawImage(part, x, y, null);
    }

    protected void mergePartX0(BufferedImage part, int multiplierX) {
        int x = (int) screener.getInnerRect().getWidth() * multiplierX;
        int y = 0;
        getGraphics().drawImage(part, x, y, null);
    }

    protected void mergePartSS(BufferedImage part) {
        int x = screener.getDocumentScrollX();
        int y = screener.getDocumentScrollY();
        getGraphics().drawImage(part, x, y, null);
    }
}
