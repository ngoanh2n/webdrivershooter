package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

public class FrameShooter extends PageShooter {
    @Override
    public PageOperator byScroll0(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return (int) framer.getInnerRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return (int) framer.getInnerRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return true;
            }
        };
    }

    @Override
    public PageOperator byScrollY(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return (int) screener.getInnerRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return (int) screener.getOuterRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return imageHeight() == part.getHeight(null);
            }
        };
    }

    @Override
    public PageOperator byScrollX(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return (int) screener.getOuterRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return (int) screener.getInnerRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return imageWidth() == part.getWidth(null);
            }
        };
    }

    @Override
    public PageOperator byScrollXY(PageOptions options, WebDriver driver) {
        return new FrameOperator((FrameOptions) options, driver) {
            @Override
            protected int imageWidth() {
                return (int) screener.getOuterRect().getWidth();
            }

            @Override
            protected int imageHeight() {
                return (int) screener.getOuterRect().getHeight();
            }

            @Override
            protected boolean imageFull(@Nonnull BufferedImage part) {
                return imageWidth() == part.getWidth(null) && imageHeight() == part.getHeight(null);
            }
        };
    }

    //-------------------------------------------------------------------------------//

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
}
