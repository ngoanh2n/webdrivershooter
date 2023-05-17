package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public interface ShooterStrategy<Operator extends ShooterOperator> {
    default Screenshot shoot(ShooterOptions options, WebDriver driver, Operator operator) {
        switch (options.shooter()) {
            case 1:
                return shootViewport(options, driver, operator);
            case 2:
                return shootVerticalScroll(options, driver, operator);
            case 3:
                return shootHorizontalScroll(options, driver, operator);
            default:
                return shootFullScroll(options, driver, operator);
        }
    }

    Screenshot shootViewport(ShooterOptions options, WebDriver driver, Operator operator);

    Screenshot shootVerticalScroll(ShooterOptions options, WebDriver driver, Operator operator);

    Screenshot shootHorizontalScroll(ShooterOptions options, WebDriver driver, Operator operator);

    Screenshot shootFullScroll(ShooterOptions options, WebDriver driver, Operator operator);
}
