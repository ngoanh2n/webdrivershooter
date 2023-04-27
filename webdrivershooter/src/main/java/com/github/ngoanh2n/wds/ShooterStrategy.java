package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public abstract class ShooterStrategy<Options extends ShooterOptions, Operator extends ShooterOperator<Options>> {
    protected abstract Operator viewport(Options options, WebDriver driver);

    protected abstract Operator verticalScroll(Options options, WebDriver driver);

    protected abstract Operator horizontalScroll(Options options, WebDriver driver);
}
