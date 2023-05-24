package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.WebDriverTestNG;
import org.openqa.selenium.WebDriver;

public class WDSTestNG extends WebDriverTestNG implements WebDriverProvider {
    public WDSTestNG() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    @Override
    public WebDriver provide() {
        if (iTestResult != null) {
            lookupDriver(iTestResult, BO);
        }
        return driver;
    }
}
