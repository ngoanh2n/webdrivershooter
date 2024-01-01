package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Dimension;
import com.github.ngoanh2n.ImageUtils;
import com.github.ngoanh2n.Rectangle;
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
     * @param target  The iframe element to support {@link #createScreener(WebElement...)}.<br>
     */
    protected FrameOperator(ShooterOptions options, WebDriver driver, WebElement target) {
        super(options, driver, target);
    }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected Screener createScreener(WebElement... targets) {
        WebElement frame = targets[0];
        super.createScreener().scrollIntoView(frame);

        framer = new Screener(options, driver, frame);
        driver.switchTo().frame(frame);
        return new Screener(options, driver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected List<WebElement> getElements() {
        return getElements(options.locators());
    }

    //-------------------------------------------------------------------------------//


    /**
     * {@inheritDoc}
     */
    protected void mergeShot(Shot shot) {
        solveFrameShot(shot);
        super.mergeShot(shot);
    }

    /**
     * Cut image defined by a specified rectangular iframe.
     *
     * @param shot The larger image contains iframe.
     */
    protected void solveFrameShot(Shot shot) {
        int x = framer.getInnerRect().getX();
        int y = framer.getInnerRect().getY();
        int w = framer.getInnerRect().getWidth();
        int h = framer.getInnerRect().getHeight();

        BufferedImage image = shot.getImage();
        Rectangle fRect = new Rectangle(x, y, w, h);
        BufferedImage fImage = ImageUtils.crop(image, fRect);

        shot.setImage(fImage);
        shot.getRect().getLocation().setX(0);
        shot.getRect().setSize(new Dimension(w, h));
    }
}
