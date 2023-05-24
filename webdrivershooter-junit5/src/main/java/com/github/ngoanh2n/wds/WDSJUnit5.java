package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.WebDriverJUnit5;
import org.openqa.selenium.WebDriver;

public class WDSJUnit5 extends WebDriverJUnit5 implements WebDriverProvider {
    public WDSJUnit5() { /* No implementation necessary */ }

    //-------------------------------------------------------------------------------//

    @Override
    public WebDriver provide() {
        if (invocationContext != null) {
            lookupDriver(invocationContext, BO);
        }
        return driver;
    }
}
