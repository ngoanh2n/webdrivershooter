package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public abstract class ShooterStrategy<Options extends ShooterOptions, Operator extends ShooterOperator<Options>> {
    protected abstract Operator byScroll0(Options options, WebDriver driver);

    protected abstract Operator byScrollY(Options options, WebDriver driver);

    protected abstract Operator byScrollX(Options options, WebDriver driver);

    protected abstract Operator byScrollXY(Options options, WebDriver driver);
}
