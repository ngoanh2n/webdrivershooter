package com.github.ngoanh2n.wds;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author ngoanh2n
 */
public class BeforeAllTest extends WDSJUnit5Test {
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
}
