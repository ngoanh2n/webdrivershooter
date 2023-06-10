package com.github.ngoanh2n.wds;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author ngoanh2n
 */
public class BeforeMethodTest extends WDSTestNGTest {
    @BeforeClass
    protected void beforeClass() {
        Assert.assertThrows(ShooterException.NullDriverProvided.class, WebDriverShooter::getDriver);
    }

    @BeforeMethod
    protected void beforeMethod() {
        createWebDriver();
        Assert.assertNotNull(WebDriverShooter.getDriver());
    }

    @Test
    protected void test() {
        Assert.assertNotNull(WebDriverShooter.getDriver());
    }
}
