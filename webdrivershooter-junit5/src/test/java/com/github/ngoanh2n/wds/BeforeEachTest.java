package com.github.ngoanh2n.wds;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author ngoanh2n
 */
public class BeforeEachTest extends WDSJUnit5Test {
    @BeforeAll
    static void beforeAll() {
        Assertions.assertThrows(ShooterException.NullDriverProvided.class, WebDriverShooter::getDriver);
    }

    @BeforeEach
    void beforeEach() {
        createWebDriver();
        Assertions.assertNotNull(WebDriverShooter.getDriver());
    }

    @Test
    void test() {
        Assertions.assertNotNull(WebDriverShooter.getDriver());
    }
}
