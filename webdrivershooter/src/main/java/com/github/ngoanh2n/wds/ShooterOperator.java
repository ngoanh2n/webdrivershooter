package com.github.ngoanh2n.wds;

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
        this.screener = screener(elements);
        this.screenshot = createScreenshot();
    }

    //-------------------------------------------------------------------------------//

    protected abstract Screener screener(WebElement... elements);

    protected abstract int imageWidth();

    protected abstract int imageHeight();

    protected abstract boolean imageFull(BufferedImage part);

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

    protected Screenshot createScreenshot() {
        Rectangle[] rectangles = getRectangles(options.elements());
        Color maskedColor = options.maskedColor();
        boolean isExcepted = options.isExcepted();
        return new Screenshot(imageWidth(), imageHeight(), rectangles, maskedColor, isExcepted);
    }

    protected Rectangle[] getRectangles(List<WebElement> elements) {
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
        return rectangles.toArray(new Rectangle[]{});
    }
}
