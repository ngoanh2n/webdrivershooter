package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.WebDriverTestNG;
import org.openqa.selenium.WebDriver;

/**
 * Provide {@link WebDriver} from the current TestNG test to {@link WebDriverShooter}.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/webdrivershooter">ngoanh2n/webdrivershooter</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter-testng">com.github.ngoanh2n:webdrivershooter-testng</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
public class WDSTestNG extends WebDriverTestNG implements WebDriverProvider {
    /**
     * Default constructor.
     */
    public WDSTestNG() { /**/ }

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
