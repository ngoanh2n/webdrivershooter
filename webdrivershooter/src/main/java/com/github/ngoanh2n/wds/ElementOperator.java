package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public abstract class ElementOperator extends ShooterOperator<ElementOptions> {
    protected ElementOperator(ElementOptions options, WebDriver driver) {
        super(options, driver);
    }
}
