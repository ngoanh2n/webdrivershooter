package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import java.awt.image.BufferedImage;

/**
 * Take page screenshot.<br><br>
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
public class PageShooter extends WebDriverShooter<PageOperator> {
    /**
     * Default constructor.
     */
    protected PageShooter() { /**/ }

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
        BufferedImage shot = shoot(driver);
        operator.mergeShot0Y(shot, 0);
        return operator.getScreenshot();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Screenshot shootVerticalScroll(ShooterOptions options, WebDriver driver, PageOperator operator) {
        int parts = operator.getPartsY();

        for (int part = 0; part < parts; part++) {
            operator.scrollSY(part);
            operator.sleep();

            BufferedImage shot = shoot(driver);
            operator.mergeShot0Y(shot, part);

            if (operator.isImageFull(shot)) {
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
        int parts = operator.getPartsX();

        for (int part = 0; part < parts; part++) {
            operator.scrollXS(part);
            operator.sleep();

            BufferedImage shot = shoot(driver);
            operator.mergeShotX0(shot, part);

            if (operator.isImageFull(shot)) {
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

                BufferedImage shot = shoot(driver);
                operator.mergeShotSS(shot);

                if (operator.isImageFull(shot)) {
                    operator.getScreenshot().dispose();
                    break;
                }
            }
        }
        return operator.getScreenshot();
    }
}
