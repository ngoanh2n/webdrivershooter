package com.github.ngoanh2n.wds;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public abstract class ElementOperator extends ShooterOperator<ElementOptions> {
    protected WebElement element;

    protected ElementOperator(ElementOptions options, WebDriver driver) {
        super(options, driver);
        this.element = options.element();
        this.screener = Screener.element(options.checkDPR(), driver, element);
        this.screener.scrollElementIntoView(element);
    }

    //-------------------------------------------------------------------------------//

    protected void scrollSY(int multiplierY) {
        int pointX = screener.getElementScrollX(element);
        int pointY = multiplierY * screener.getInnerRect().getHeight();
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    protected void scrollXS(int multiplierX) {
        int pointX = multiplierX * screener.getInnerRect().getWidth();
        int pointY = screener.getElementScrollY(element);
        screener.scrollElementToPoint(element, new Point(pointX, pointY));
    }

    protected void scrollXY(int multiplierX, int multiplierY) {
        int pointX = multiplierX * screener.getInnerRect().getWidth();
        int pointY = multiplierY * screener.getInnerRect().getHeight();
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

    protected BufferedImage getElementPart(BufferedImage part) {
        int x = screener.getOuterRect().getX();
        int y = screener.getOuterRect().getY();
        int w = screener.getInnerRect().getWidth();
        int h = screener.getInnerRect().getHeight();
        return part.getSubimage(x, y, w, h);
    }
}
