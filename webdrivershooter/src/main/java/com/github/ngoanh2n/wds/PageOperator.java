package com.github.ngoanh2n.wds;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import java.awt.image.BufferedImage;

public abstract class PageOperator extends ShooterOperator<PageOptions> {
    protected PageOperator(PageOptions options, WebDriver driver) {
        super(options, driver);
    }

    //-------------------------------------------------------------------------------//

    protected void scrollSY(int multiplierY) {
        int pointX = screener.getDocumentScrollX();
        int pointY = screener.getInnerRect().getHeight() * multiplierY;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    protected void scrollXS(int multiplierX) {
        int pointX = screener.getInnerRect().getWidth() * multiplierX;
        int pointY = screener.getDocumentScrollY();
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    protected void scrollXY(int multiplierX, int multiplierY) {
        int pointX = screener.getInnerRect().getWidth() * multiplierX;
        int pointY = screener.getInnerRect().getHeight() * multiplierY;
        screener.scrollToPoint(new Point(pointX, pointY));
    }

    protected void mergePart0Y(BufferedImage part, int multiplierY) {
        int x = 0;
        int y = screener.getInnerRect().getHeight() * multiplierY;
        getGraphics().drawImage(part, x, y, null);
    }

    protected void mergePartX0(BufferedImage part, int multiplierX) {
        int x = screener.getInnerRect().getWidth() * multiplierX;
        int y = 0;
        getGraphics().drawImage(part, x, y, null);
    }

    protected void mergePartSS(BufferedImage part) {
        int x = screener.getDocumentScrollX();
        int y = screener.getDocumentScrollY();
        getGraphics().drawImage(part, x, y, null);
    }
}
