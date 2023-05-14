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
        screenshot.mergePart(part, x, y);
    }

    protected void mergePartX0(BufferedImage part, int multiplierX) {
        int x = (int) screener.getInnerRect().getWidth() * multiplierX;
        int y = 0;
        screenshot.mergePart(part, x, y);
    }

    protected void mergePartSS(BufferedImage part) {
        int x = screener.getDocumentScrollX();
        int y = screener.getDocumentScrollY();
        screenshot.mergePart(part, x, y);
    }
}
