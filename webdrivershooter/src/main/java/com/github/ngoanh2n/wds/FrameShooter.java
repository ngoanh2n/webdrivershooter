package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.image.BufferedImage;

/**
 * Take iframe screenshot.<br><br>
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
public class FrameShooter extends PageShooter {
    private final WebElement frame;

    /**
     * Construct a new {@link FrameShooter}.
     *
     * @param frame The iframe to be captured.
     */
    public FrameShooter(WebElement frame) {
        this.frame = frame;
    }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected PageOperator operator(ShooterOptions options, WebDriver driver) {
        return new FrameOperator(options, driver, frame);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootViewport(ShooterOptions options, WebDriver driver, PageOperator operator) {
        BufferedImage part = screenshot(driver);
        ((FrameOperator) operator).mergePart00(part);

        if (operator.isImageFull(part)) {
            operator.getScreenshot().dispose();
        }
        return operator.getScreenshot();
    }
}
