package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
public class ElementShooter extends PageShooter {
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
    protected ShooterOperator operator(ShooterOptions options, WebDriver driver) {
        return new ElementOperator(options, driver, element);
    }
}
