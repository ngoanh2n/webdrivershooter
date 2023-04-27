package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public abstract class ShooterOperator<Options extends ShooterOptions> {
    protected Options options;
    protected WebDriver driver;
    protected Screener screener;
    protected Screenshot screenshot;
    protected BufferedImage image;
    protected Graphics2D graphics;
}
