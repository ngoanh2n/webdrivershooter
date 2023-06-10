package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.WebDriverTestNG;
import org.openqa.selenium.WebDriver;

/**
 * Provide {@link WebDriver} from the current TestNG test to {@link WebDriverShooter}.
 *
 * @author ngoanh2n
 */
public class WDSTestNG extends WebDriverTestNG implements WebDriverProvider {
    /**
     * Default constructor.
     */
    public WDSTestNG() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    /**
     * {@inheritDoc}
     */
    @Override
    public WebDriver provide() {
        if (iTestResult != null) {
            lookupDriver(iTestResult, BO);
        }
        return driver;
    }
}
