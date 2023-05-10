package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

public interface ShooterStrategy<Operator extends ShooterOperator> {
    default Screenshot shoot(ShooterOptions options, WebDriver driver, Operator operator) {
        switch (options.shooterStrategy()) {
            case 1:
                return shootViewport(options, driver, operator);
            case 2:
                return shootVerticalScroll(options, driver, operator);
            case 3:
                return shootHorizontalScroll(options, driver, operator);
            default:
                return shootBothDirectionScroll(options, driver, operator);
        }
    }

    Screenshot shootViewport(ShooterOptions options, WebDriver driver, Operator operator);

    Screenshot shootVerticalScroll(ShooterOptions options, WebDriver driver, Operator operator);

    Screenshot shootHorizontalScroll(ShooterOptions options, WebDriver driver, Operator operator);

    Screenshot shootBothDirectionScroll(ShooterOptions options, WebDriver driver, Operator operator);
}
