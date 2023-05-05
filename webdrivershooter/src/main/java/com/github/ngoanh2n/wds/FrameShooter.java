package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

class FrameShooter extends PageShooter {
    @Override
    protected Screenshot shootScroll0(PageOptions options, PageOperator operator, WebDriver driver) {
        operator.sleep();

        BufferedImage part = screenshot(driver);
        ((FrameOperator) operator).mergePart00(part);

        if (operator.imageFull(part)) {
            operator.getGraphics().dispose();
        }
        return operator.getScreenshot();
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected PageOperator byScroll0(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return framer.getInnerRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return framer.getInnerRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return true;
            }
        };
    }

    @Override
    protected PageOperator byScrollY(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return screener.getInnerRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return screener.getOuterRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return imageHeight() == part.getHeight(null);
            }
        };
    }

    @Override
    protected PageOperator byScrollX(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return screener.getOuterRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return screener.getInnerRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return imageWidth() == part.getWidth(null);
            }
        };
    }

    @Override
    protected PageOperator byScrollXY(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return screener.getOuterRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return screener.getOuterRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return imageWidth() == part.getWidth(null) && imageHeight() == part.getHeight(null);
            }
        };
    }
}
