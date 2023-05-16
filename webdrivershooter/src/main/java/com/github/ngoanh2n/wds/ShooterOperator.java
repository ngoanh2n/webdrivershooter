package com.github.ngoanh2n.wds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public abstract class ShooterOperator {
    protected ShooterOptions options;
    protected WebDriver driver;
    protected boolean checkDPR;
    protected Screener screener;
    protected Screenshot screenshot;

    protected ShooterOperator(ShooterOptions options, WebDriver driver, WebElement... elements) {
        this.options = options;
        this.driver = driver;
        this.checkDPR = options.checkDPR();
        this.screener = createScreener(elements);
        this.screenshot = createScreenshot();
    }

    //-------------------------------------------------------------------------------//

    protected Screener createScreener(WebElement... elements) {
        return Screener.page(checkDPR, driver);
    }

    protected Screenshot createScreenshot() {
        int width = getImageWidth();
        int height = getImageHeight();
        List<WebElement> elements = getElements();
        List<Rectangle> areas = getRectangles(elements);
        boolean isMasked = options.isMasked();
        Color maskedColor = options.maskedColor();
        return new Screenshot(width, height, areas, isMasked, maskedColor);
    }

    //-------------------------------------------------------------------------------//


    protected List<WebElement> getElements() {
        if (options.elements().size() > 0) {
            return options.elements();
        }
        return getElements(options.locators());
    }

    protected List<WebElement> getElements(List<By> locators) {
        List<WebElement> elements = new ArrayList<>();
        for (By locator : locators) {
            WebElement element = driver.findElement(locator);
            elements.add(element);
        }
        return elements;
    }

    protected List<Rectangle> getRectangles(List<WebElement> elements) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (WebElement element : elements) {
            int x = (int) (element.getLocation().getX() * screener.getDPR());
            int y = (int) (element.getLocation().getY() * screener.getDPR());
            int w = (int) (element.getSize().getWidth() * screener.getDPR());
            int h = (int) (element.getSize().getHeight() * screener.getDPR());

            Point location = new Point(x, y);
            Dimension size = new Dimension(w, h);
            rectangles.add(new Rectangle(location, size));
        }
        return rectangles;
    }

    protected int getImageWidth() {
        switch (options.shooter()) {
            case 1:
            case 2:
                return (int) screener.getInnerRect().getWidth();
            default:
                return (int) screener.getOuterRect().getWidth();
        }
    }

    protected int getImageHeight() {
        switch (options.shooter()) {
            case 1:
            case 3:
                return (int) screener.getInnerRect().getHeight();
            default:
                return (int) screener.getOuterRect().getHeight();
        }
    }

    protected boolean isImageFull(BufferedImage part) {
        switch (options.shooter()) {
            case 1:
            case 2:
                return getImageHeight() == part.getHeight(null);
            case 3:
                return getImageWidth() == part.getWidth(null);
            default:
                return getImageWidth() == part.getWidth(null) && getImageHeight() == part.getHeight(null);
        }
    }

    //-------------------------------------------------------------------------------//

    protected void sleep() {
        try {
            Thread.sleep(options.scrollDelay());
        } catch (InterruptedException e) {
            throw new ShooterException(e);
        }
    }

    protected int getPartsY() {
        int outerH = (int) screener.getOuterRect().getHeight();
        int innerH = (int) screener.getInnerRect().getHeight();
        return (int) Math.ceil(((double) outerH) / innerH);
    }

    protected int getPartsX() {
        int outerW = (int) screener.getOuterRect().getWidth();
        int innerW = (int) screener.getInnerRect().getWidth();
        return (int) Math.ceil(((double) outerW) / innerW);
    }

    protected Screenshot getScreenshot() {
        return screenshot;
    }
}
