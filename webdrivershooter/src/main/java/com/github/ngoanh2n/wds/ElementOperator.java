package com.github.ngoanh2n.wds;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;

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
}
