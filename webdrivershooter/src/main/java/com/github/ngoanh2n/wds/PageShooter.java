package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import java.awt.image.BufferedImage;

/**
 * Take page screenshot.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class PageShooter extends WebDriverShooter<PageOperator> {
    /**
     * Default constructor.
     */
    protected PageShooter() { /* No implementation necessary */ }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PageOperator operator(ShooterOptions options, WebDriver driver) {
        return new PageOperator(options, driver);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootViewport(ShooterOptions options, WebDriver driver, PageOperator operator) {
        BufferedImage part = screenshot(driver);
        operator.mergePart0Y(part, 0);
        return operator.getScreenshot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootVerticalScroll(ShooterOptions options, WebDriver driver, PageOperator operator) {
        int partsY = operator.getPartsY();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollSY(partY);
            operator.sleep();

            BufferedImage part = screenshot(driver);
            operator.mergePart0Y(part, partY);

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
    public Screenshot shootHorizontalScroll(ShooterOptions options, WebDriver driver, PageOperator operator) {
        int partsX = operator.getPartsX();

        for (int partX = 0; partX < partsX; partX++) {
            operator.scrollXS(partX);
            operator.sleep();

            BufferedImage part = screenshot(driver);
            operator.mergePartX0(part, partX);

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
    public Screenshot shootFullScroll(ShooterOptions options, WebDriver driver, PageOperator operator) {
        int partsY = operator.getPartsY();
        int partsX = operator.getPartsX();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollXY(0, partY);

            for (int partX = 0; partX < partsX; partX++) {
                operator.scrollXY(partX, partY);
                operator.sleep();

                BufferedImage part = screenshot(driver);
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
