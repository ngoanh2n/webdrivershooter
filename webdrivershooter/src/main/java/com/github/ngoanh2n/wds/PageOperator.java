package com.github.ngoanh2n.wds;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

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
}
