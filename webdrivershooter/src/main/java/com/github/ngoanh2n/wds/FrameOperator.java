package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Operate coordinates and rectangles on screen for iframe.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
@ParametersAreNonnullByDefault
public class FrameOperator extends PageOperator {
    /**
     * The {@link Screener} for iframe.
     */
    protected Screener framer;

    /**
     * Construct a new {@link FrameOperator}.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @param frame   The iframe element to support {@link #createScreener(WebElement...)}.<br>
     */
    protected FrameOperator(ShooterOptions options, WebDriver driver, WebElement frame) {
        super(options, driver, frame);
    }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected Screener createScreener(WebElement... elements) {
        WebElement frame = elements[0];
        super.createScreener().scrollElementIntoView(frame);

        framer = new Screener(checkDPR, driver, frame);
        driver.switchTo().frame(frame);
        return new Screener(checkDPR, driver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<WebElement> getElements() {
        return getElements(options.locators());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isImageFull(BufferedImage part) {
        switch (options.shooter()) {
            case 1:
                return true;
            case 2:
                return getImageHeight() == part.getHeight(null);
            case 3:
                return getImageWidth() == part.getWidth(null);
            default:
                return getImageWidth() == part.getWidth(null) && getImageHeight() == part.getHeight(null);
        }
    }

    //-------------------------------------------------------------------------------//

    /**
     * Draw the specified part over the current image of {@link Screenshot} with its top-left corner at (0,0).
     *
     * @param part The specified part to be drawn over the current {@link Screenshot}.
     */
    protected void mergePart00(BufferedImage part) {
        part = getFrameImage(part);
        screenshot.mergePart(part, 0, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mergePart0Y(BufferedImage part, int multiplierY) {
        part = getFrameImage(part);
        super.mergePart0Y(part, multiplierY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mergePartX0(BufferedImage part, int multiplierX) {
        part = getFrameImage(part);
        super.mergePartX0(part, multiplierX);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mergePartSS(BufferedImage part) {
        part = getFrameImage(part);
        super.mergePartSS(part);
    }

    /**
     * Cut image defined by a specified rectangular iframe.
     *
     * @param outerImage The larger image contains iframe.
     * @return The iframe image.
     */
    protected BufferedImage getFrameImage(BufferedImage outerImage) {
        int x = (int) framer.getInnerRect().getX();
        int y = (int) framer.getInnerRect().getY();
        int w = (int) framer.getInnerRect().getWidth();
        int h = (int) framer.getInnerRect().getHeight();
        return outerImage.getSubimage(x, y, w, h);
    }
}
