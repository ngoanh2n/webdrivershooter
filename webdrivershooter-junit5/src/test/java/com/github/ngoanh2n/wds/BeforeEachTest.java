package com.github.ngoanh2n.wds;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public class BeforeEachTest extends WDSJUnit5Test {
    @BeforeAll
    static void beforeAll() {
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
