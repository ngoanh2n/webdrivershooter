package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Operate coordinates and rectangles on screen for iframe.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/webdrivershooter">ngoanh2n/webdrivershooter</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter">com.github.ngoanh2n:webdrivershooter</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
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
        super.createScreener().scrollIntoView(frame);

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
    protected boolean isImageFull(BufferedImage shot) {
        return switch (options.shooter()) {
            case 1 -> true;
            case 2 -> getShotImageHeight() == shot.getHeight(null);
            case 3 -> getShotImageWidth() == shot.getWidth(null);
            default -> getShotImageWidth() == shot.getWidth(null) && getShotImageHeight() == shot.getHeight(null);
        };
    }

    //-------------------------------------------------------------------------------//

    /**
     * Draw the specified shot over the current image of {@link Screenshot} with its top-left corner at (0,0).
     *
     * @param shot The specified shot to be drawn over the current {@link Screenshot}.
     */
    protected void mergeShot00(BufferedImage shot) {
        shot = getFrameShot(shot);
        shotImage.merge(shot, new Point(0, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mergeShot0Y(BufferedImage shot, int part) {
        shot = getFrameShot(shot);
        super.mergeShot0Y(shot, part);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mergeShotX0(BufferedImage shot, int part) {
        shot = getFrameShot(shot);
        super.mergeShotX0(shot, part);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mergeShotSS(BufferedImage shot) {
        shot = getFrameShot(shot);
        super.mergeShotSS(shot);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Cut image defined by a specified rectangular iframe.
     *
     * @param shot The larger image contains iframe.
     * @return The iframe image.
     */
    protected BufferedImage getFrameShot(BufferedImage shot) {
        int x = framer.getInnerRect().getX();
        int y = framer.getInnerRect().getY();
        int w = framer.getInnerRect().getWidth();
        int h = framer.getInnerRect().getHeight();
        return shot.getSubimage(x, y, w, h);
    }
}
