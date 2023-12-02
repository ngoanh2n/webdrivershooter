package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.image.BufferedImage;

/**
 * Take element screenshot.<br><br>
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
public class ElementShooter extends WebDriverShooter<ElementOperator> {
    private final WebElement element;

    /**
     * Construct a new {@link ElementShooter}.
     *
     * @param element The element to be captured.
     */
    public ElementShooter(WebElement element) {
        this.element = element;
    }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected ElementOperator operator(ShooterOptions options, WebDriver driver) {
        return new ElementOperator(options, driver, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootViewport(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        Screenshot screenshot = page(ShooterOptions.builder().shootViewport().build(), driver);
        BufferedImage image = screenshot.getImage();
        operator.mergeShot00(image);

        if (operator.isImageFull(image)) {
            operator.getScreenshot().dispose();
        }
        return operator.getScreenshot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootVerticalScroll(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        int parts = operator.getPartsY();

        for (int part = 0; part < parts; part++) {
            operator.scrollSY(part);
            operator.sleep();

            Screenshot screenshot = page(driver);
            BufferedImage image = screenshot.getImage();
            operator.mergeShot0S(image);

            if (operator.isImageFull(image)) {
                operator.screenshot.dispose();
                break;
            }
        }
        return operator.getScreenshot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootHorizontalScroll(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        int parts = operator.getPartsX();

        for (int part = 0; part < parts; part++) {
            operator.scrollXS(part);
            operator.sleep();

            Screenshot screenshot = page(driver);
            BufferedImage image = screenshot.getImage();
            operator.mergeShotS0(image);

            if (operator.isImageFull(image)) {
                operator.getScreenshot().dispose();
                break;
            }
        }
        return operator.getScreenshot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootFullScroll(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        int partsY = operator.getPartsY();
        int partsX = operator.getPartsX();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollXY(0, partY);

            for (int partX = 0; partX < partsX; partX++) {
                operator.scrollXY(partX, partY);
                operator.sleep();

                Screenshot screenshot = page(driver);
                BufferedImage image = screenshot.getImage();
                operator.mergeShotSS(image);

                if (operator.isImageFull(image)) {
                    operator.getScreenshot().dispose();
                    break;
                }
            }
        }
        return operator.getScreenshot();
    }
}
