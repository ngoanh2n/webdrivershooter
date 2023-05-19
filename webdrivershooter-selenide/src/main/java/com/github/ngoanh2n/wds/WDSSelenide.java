package com.github.ngoanh2n.wds;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WDSSelenide implements WebDriverProvider {
    private static final Logger log = LoggerFactory.getLogger(WDSSelenide.class);

    public WDSSelenide() { /* No implementation necessary */ }

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
