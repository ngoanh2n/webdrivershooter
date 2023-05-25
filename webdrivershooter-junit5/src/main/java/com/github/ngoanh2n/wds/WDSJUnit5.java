package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.WebDriverJUnit5;
import org.openqa.selenium.WebDriver;

/**
 * Provide {@link WebDriver} from the current JUnit5 test to {@link WebDriverShooter}.
 *
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class WDSJUnit5 extends WebDriverJUnit5 implements WebDriverProvider {
    /**
     * Default constructor.
     */
    public WDSJUnit5() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDriver provide() {
        if (invocationContext != null) {
            lookupDriver(invocationContext, BO);
        }
        return driver;
    }
}
