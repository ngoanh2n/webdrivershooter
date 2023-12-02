package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

/**
 * Strategies to take screenshot.<br><br>
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
        return switch (options.shooter()) {
            case 1 -> shootViewport(options, driver, operator);
            case 2 -> shootVerticalScroll(options, driver, operator);
            case 3 -> shootHorizontalScroll(options, driver, operator);
            default -> shootFullScroll(options, driver, operator);
        };
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
