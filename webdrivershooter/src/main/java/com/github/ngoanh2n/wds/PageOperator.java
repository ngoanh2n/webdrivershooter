package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public abstract class PageOperator extends ShooterOperator<PageOptions> {
    protected PageOperator(PageOptions options, WebDriver driver) {
        super(options, driver);
    }
}
