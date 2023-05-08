package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public abstract class ShooterOperator<Options extends ShooterOptions> {
    protected Options options;
    protected WebDriver driver;
    protected Screener screener;
    protected BufferedImage image;
    protected Graphics2D graphics;
    protected Screenshot screenshot;

    protected ShooterOperator(Options options, WebDriver driver) {
        this.options = options;
        this.driver = driver;
        this.screener = Screener.page(options.checkDPR(), driver);
        this.initializeImage();
        this.initializeScreenshot();
    }

    //-------------------------------------------------------------------------------//

    protected abstract int imageWidth();

    protected abstract int imageHeight();

    protected abstract boolean imageFull(@Nonnull BufferedImage part);

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

    protected Graphics2D getGraphics() {
        return graphics;
    }

    protected Screenshot getScreenshot() {
        return screenshot;
    }

    protected Rectangle[] getRectangles(WebElement... elements) {
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

    //-------------------------------------------------------------------------------//

    private void initializeImage() {
        int w = imageWidth();
        int h = imageHeight();
        int t = BufferedImage.TYPE_INT_ARGB;
        image = new BufferedImage(w, h, t);
        graphics = image.createGraphics();
    }

    private void initializeScreenshot() {
        Color maskedColor = options.maskedColor();
        boolean isExcepted = options.isExcepted();
        Rectangle[] rectangles = getRectangles(options.elements());
        screenshot = new Screenshot(image, rectangles, maskedColor, isExcepted);
    }
}
