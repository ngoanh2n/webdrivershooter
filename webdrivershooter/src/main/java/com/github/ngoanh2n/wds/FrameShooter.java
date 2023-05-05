package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

class FrameShooter extends PageShooter {
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
}
