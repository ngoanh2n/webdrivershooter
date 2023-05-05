package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

class ElementShooter extends WebDriverShooter<ElementOptions, ElementOperator> {
    @Override
    protected ElementOperator byScroll0(ElementOptions options, WebDriver driver) {
        return new ElementOperator(options, driver) {
            @Override
            protected int imageWidth() {
                return screener.getInnerRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return screener.getInnerRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return true;
            }
        };
    }

    @Override
    protected ElementOperator byScrollY(ElementOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected ElementOperator byScrollX(ElementOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected ElementOperator byScrollXY(ElementOptions options, WebDriver driver) {
        return null;
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected Screenshot shootScroll0(ElementOptions options, ElementOperator operator, WebDriver driver) {
        operator.sleep();

        Screenshot screenshot = page(driver);
        BufferedImage part = screenshot.getImage();
        operator.mergePart00(part);

        if (operator.imageFull(part)) {
            operator.getGraphics().dispose();
        }
        return operator.getScreenshot();
    }

    @Override
    protected Screenshot shootScrollY(ElementOptions options, ElementOperator operator, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootScrollX(ElementOptions options, ElementOperator operator, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootScrollXY(ElementOptions options, ElementOperator operator, WebDriver driver) {
        return null;
    }
}
