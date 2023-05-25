package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.image.BufferedImage;

/**
 * Take element screenshot.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
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
        Screenshot screenshot = page(driver);
        BufferedImage image = screenshot.getImage();
        operator.mergePart00(image);

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
        int partsY = operator.getPartsY();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollSY(partY);
            operator.sleep();

            Screenshot screenshot = page(driver);
            BufferedImage image = screenshot.getImage();
            operator.mergePart0S(image);

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
        int partsX = operator.getPartsX();

        for (int partX = 0; partX < partsX; partX++) {
            operator.scrollXS(partX);
            operator.sleep();

            Screenshot screenshot = page(driver);
            BufferedImage part = screenshot.getImage();
            operator.mergePartS0(part);

            if (operator.isImageFull(part)) {
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
                BufferedImage part = screenshot.getImage();
                operator.mergePartSS(part);

                if (operator.isImageFull(part)) {
                    operator.getScreenshot().dispose();
                    break;
                }
            }
        }
        return operator.getScreenshot();
    }
}
