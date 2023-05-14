package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.image.BufferedImage;

public class FrameShooter extends PageShooter {
    private final WebElement frame;

    public FrameShooter(WebElement frame) {
        this.frame = frame;
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected PageOperator operator(ShooterOptions options, WebDriver driver) {
        return new FrameOperator(options, driver, frame);
    }

    @Override
    public Screenshot shootViewport(ShooterOptions options, WebDriver driver, PageOperator operator) {
        operator.sleep();

        BufferedImage part = screenshot(driver);
        ((FrameOperator) operator).mergePart00(part);

        if (operator.isImageFull(part)) {
            operator.getScreenshot().dispose();
        }
        return operator.getScreenshot();
    }
}
