package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public interface ShooterStrategy<Options extends ShooterOptions, Operator extends ShooterOperator<Options>> {
    Operator byScroll0(Options options, WebDriver driver);

    Operator byScrollY(Options options, WebDriver driver);

    Operator byScrollX(Options options, WebDriver driver);

    Operator byScrollXY(Options options, WebDriver driver);
}
