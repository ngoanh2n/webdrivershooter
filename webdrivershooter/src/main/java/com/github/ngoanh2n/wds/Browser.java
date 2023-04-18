package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public class Browser {
    private final WebDriver driver;
    private final Double dpr;

    public Browser(WebDriver driver, ShooterOptions options) {
        this.driver = driver;
        this.dpr = 1.0;
    }
}
