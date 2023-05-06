package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public abstract class PageOperator extends ShooterOperator<PageOptions> {
    protected PageOperator(PageOptions options, WebDriver driver) {
        super(options, driver);
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
