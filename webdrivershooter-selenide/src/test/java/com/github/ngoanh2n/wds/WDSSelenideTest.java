package com.github.ngoanh2n.wds;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.ngoanh2n.EnabledIfProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author ngoanh2n
 */
@EnabledIfProperty(name = "wdc.group", value = "extension")
public class WDSSelenideTest {
    @BeforeAll
    static void beforeAll() {
        Assertions.assertThrows(ShooterException.class, WebDriverShooter::getDriver);
        createWebDriver();
        Assertions.assertNotNull(WebDriverShooter.getDriver());
    }

    @BeforeEach
    void beforeEach() {
        Assertions.assertNotNull(WebDriverShooter.getDriver());
    }

    @Test
    void test() {
        Assertions.assertNotNull(WebDriverShooter.getDriver());
    }

    private static void createWebDriver() {
        Configuration.timeout = 10 * 1000;
        Configuration.pollingInterval = 500;
        Configuration.browser = "chrome";
        Selenide.open();
    }
}
