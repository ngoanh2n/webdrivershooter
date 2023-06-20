package com.github.ngoanh2n.wds;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide {@link WebDriver} from {@link WebDriverRunner} to {@link WebDriverShooter}.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/webdrivershooter">ngoanh2n/webdrivershooter</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter-selenide">com.github.ngoanh2n:webdrivershooter-selenide</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
public class WDSSelenide implements WebDriverProvider {
    private static final Logger log = LoggerFactory.getLogger(WDSSelenide.class);

    /**
     * Default constructor.
     */
    public WDSSelenide() { /**/ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDriver provide() {
        try {
            return WebDriverRunner.getWebDriver();
        } catch (IllegalStateException ignored) {
            String msg = "WebDriver is quit";
            log.error(msg);
            throw new ShooterException(msg);
        }
    }
}
