package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

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
public class PageShooter extends WebDriverShooter {
    /**
     * Default constructor.
     */
    protected PageShooter() { /**/ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    protected Screenshot shoot(ShooterOptions options, WebDriver driver) {
        ShooterOperator operator = operator(options, driver);
        return switch (options.shooter()) {
            case 1 -> shoot00(operator, driver);
            case 2 -> shoot0Y(operator, driver);
            case 3 -> shootX0(operator, driver);
            default -> shootXY(operator, driver);
        };
    }

    /**
     * Provide a {@link ShooterOperator} implementation.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @return The {@link ShooterOperator}.
     */
    protected ShooterOperator operator(ShooterOptions options, WebDriver driver) {
        return new PageOperator(options, driver);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Take screenshot of viewport.
     *
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    protected Screenshot shoot00(ShooterOperator operator, WebDriver driver) {
        double dpr = operator.getDPR();
        operator.sleep();
        Shot.Position position = operator.scrollTo(0, 0);
        Shot shot = shoot(driver, dpr, position);
        operator.mergeShot(shot);
        return operator.getScreenshot();
    }

    /**
     * Take screenshot while scrolling vertically.
     *
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    protected Screenshot shoot0Y(ShooterOperator operator, WebDriver driver) {
        double dpr = operator.getDPR();
        int parts = operator.getPartsY();

        for (int part = 0; part < parts; part++) {
            operator.sleep();
            Shot.Position position = operator.scrollTo(0, part);
            Shot shot = shoot(driver, dpr, position);
            operator.mergeShot(shot);
        }
        return operator.getScreenshot();
    }

    /**
     * Take screenshot while scrolling horizontally.
     *
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    protected Screenshot shootX0(ShooterOperator operator, WebDriver driver) {
        double dpr = operator.getDPR();
        int parts = operator.getPartsX();

        for (int part = 0; part < parts; part++) {
            operator.sleep();
            Shot.Position position = operator.scrollTo(part, 0);
            Shot shot = shoot(driver, dpr, position);
            operator.mergeShot(shot);
        }
        return operator.getScreenshot();
    }

    /**
     * Take screenshot while scrolling vertically and horizontally.
     *
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    protected Screenshot shootXY(ShooterOperator operator, WebDriver driver) {
        double dpr = operator.getDPR();
        int partsY = operator.getPartsY();
        int partsX = operator.getPartsX();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollTo(0, partY);

            for (int partX = 0; partX < partsX; partX++) {
                operator.sleep();
                Shot.Position position = operator.scrollTo(partX, partY);
                Shot shot = shoot(driver, dpr, position);
                operator.mergeShot(shot);
            }
        }
        return operator.getScreenshot();
    }
}
