package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

/**
 * Strategies to take screenshot.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public interface ShooterStrategy<Operator extends ShooterOperator> {
    /**
     * Take screenshot.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    default Screenshot shoot(ShooterOptions options, WebDriver driver, Operator operator) {
        switch (options.shooter()) {
            case 1:
                return shootViewport(options, driver, operator);
            case 2:
                return shootVerticalScroll(options, driver, operator);
            case 3:
                return shootHorizontalScroll(options, driver, operator);
            default:
                return shootFullScroll(options, driver, operator);
        }
    }

    /**
     * Take screenshot of viewport.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    Screenshot shootViewport(ShooterOptions options, WebDriver driver, Operator operator);

    /**
     * Take screenshot while scrolling vertically.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    Screenshot shootVerticalScroll(ShooterOptions options, WebDriver driver, Operator operator);

    /**
     * Take screenshot while scrolling horizontally.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    Screenshot shootHorizontalScroll(ShooterOptions options, WebDriver driver, Operator operator);

    /**
     * Take screenshot while scrolling vertically and horizontally.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param operator The helper to operate on screen.
     * @return The {@link Screenshot}.
     */
    Screenshot shootFullScroll(ShooterOptions options, WebDriver driver, Operator operator);
}
