package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.RuntimeError;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
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
    protected Screenshot screenshot;
    protected BufferedImage image;
    protected Graphics2D graphics;

    protected ShooterOperator(Options options, WebDriver driver) {
        this.options = options;
        this.driver = driver;
        this.screener = Screener.page(options.checkDPR(), driver);
        this.screenshot = createScreenshot();
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
            throw new RuntimeError(e);
        }
    }

    protected int getPartsY() {
        int outerH = screener.getOuterRect().getHeight();
        int innerH = screener.getInnerRect().getHeight();
        return (int) Math.ceil(((double) outerH) / innerH);
    }

    protected int getPartsX() {
        int outerW = screener.getOuterRect().getWidth();
        int innerW = screener.getInnerRect().getWidth();
        return (int) Math.ceil(((double) outerW) / innerW);
    }

    protected BufferedImage getImage() {
        if (image == null) {
            int w = imageWidth();
            int h = imageHeight();
            int t = BufferedImage.TYPE_INT_ARGB;
            image = new BufferedImage(w, h, t);
            graphics = image.createGraphics();
        }
        return image;
    }

    //-------------------------------------------------------------------------------//

    private Screenshot createScreenshot() {
        List<Screengle> results = new ArrayList<>();
        if (options.ignoredElement() != null) {
            WebElement element = options.ignoredElement();
            int x = (int) (element.getLocation().getX() * screener.getDPR());
            int y = (int) (element.getLocation().getY() * screener.getDPR());
            int w = (int) (element.getSize().getWidth() * screener.getDPR());
            int h = (int) (element.getSize().getHeight() * screener.getDPR());

            Point location = new Point(x, y);
            Dimension size = new Dimension(w, h);
            results.add(Screengle.from(location, size));
        }
        Color color = options.decoratedColor();
        return new Screenshot(color, results.toArray(new Screengle[]{}));
    }
}
