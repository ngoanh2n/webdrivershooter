package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Operate coordinates and rectangles on screen for page.<br><br>
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
public class PageOperator extends ShooterOperator {
    /**
     * Construct a new {@link PageOperator}.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @param targets The element to support {@link #createScreener(WebElement...)}.<br>
     *                But it will be ignored for this case.
     */
    protected PageOperator(ShooterOptions options, WebDriver driver, WebElement... targets) {
        super(options, driver, targets);
    }

    //-------------------------------------------------------------------------------//
}
