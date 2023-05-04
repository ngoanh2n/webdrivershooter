package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

class PageShooter extends WebDriverShooter<PageOptions, PageOperator> {
    @Override
    protected Screenshot shootViewport(PageOptions options, WebDriver driver) {
        PageOperator operator = viewport(options, driver);
        BufferedImage part = screenshot(driver);
        operator.mergePart0Y(part, 0);
        return operator.getScreenshot();
    }

    @Override
    protected Screenshot shootVerticalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootHorizontalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected Screenshot shootBothDirectionScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected PageOperator viewport(PageOptions options, WebDriver driver) {
        return new PageOperator(options, driver) {
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
                return imageHeight() == part.getHeight(null);
            }
        };
    }

    @Override
    protected PageOperator verticalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected PageOperator horizontalScroll(PageOptions options, WebDriver driver) {
        return null;
    }

    @Override
    protected PageOperator bothDirectionScroll(PageOptions options, WebDriver driver) {
        return null;
    }
}
