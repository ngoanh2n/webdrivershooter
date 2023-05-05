package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class FrameOperator extends PageOperator {
    protected FrameOperator(PageOptions options, WebDriver driver) {
        super(options, driver);
    }
}
