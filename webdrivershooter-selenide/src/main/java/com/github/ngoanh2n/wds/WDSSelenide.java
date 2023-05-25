package com.github.ngoanh2n.wds;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide {@link WebDriver} from {@link WebDriverRunner} to {@link WebDriverShooter}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class WDSSelenide implements WebDriverProvider {
    private static final Logger log = LoggerFactory.getLogger(WDSSelenide.class);

    /**
     * Default constructor.
     */
    public WDSSelenide() { /* No implementation necessary */ }

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
