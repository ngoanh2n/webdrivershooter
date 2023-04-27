package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public abstract class ShooterStrategy<Options extends ShooterOptions, Operator extends ShooterOperator<Options>> {
    protected abstract Operator viewport(Options options, WebDriver driver);
}
