package com.github.ngoanh2n.wds;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class BeforeClassTest extends WDSTestNGTest {
    @BeforeClass
    protected void beforeClass() {
        Assert.assertThrows(ShooterException.class, WebDriverShooter::getDriver);
        createWebDriver();
        Assert.assertNotNull(WebDriverShooter.getDriver());
    }

    @BeforeMethod
    protected void beforeMethod() {
        Assert.assertNotNull(WebDriverShooter.getDriver());
    }

    @Test
    protected void test() {
        Assert.assertNotNull(WebDriverShooter.getDriver());
    }
}
