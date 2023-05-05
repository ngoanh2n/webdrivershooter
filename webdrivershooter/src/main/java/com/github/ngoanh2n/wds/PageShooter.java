package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.awt.image.BufferedImage;

class PageShooter extends WebDriverShooter<PageOptions, PageOperator> {
    @Override
    protected PageOperator byScroll0(PageOptions options, WebDriver driver) {
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
    protected PageOperator byScrollY(PageOptions options, WebDriver driver) {
        return new PageOperator(options, driver) {
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
        return new PageOperator(options, driver) {
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
        return new PageOperator(options, driver) {
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

    //-------------------------------------------------------------------------------//

    @Override
    protected Screenshot shootScroll0(PageOptions options, PageOperator operator, WebDriver driver) {
        BufferedImage part = screenshot(driver);
        operator.mergePart0Y(part, 0);
        return operator.getScreenshot();
    }

    @Override
    protected Screenshot shootScrollY(PageOptions options, PageOperator operator, WebDriver driver) {
        int partsY = operator.getPartsY();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollSY(partY);
            operator.sleep();

            BufferedImage part = screenshot(driver);
            operator.mergePart0Y(part, partY);

            if (operator.imageFull(part)) {
                operator.getGraphics().dispose();
                break;
            }
        }
        return operator.getScreenshot();
    }

    @Override
    protected Screenshot shootScrollX(PageOptions options, PageOperator operator, WebDriver driver) {
        int partsX = operator.getPartsX();

        for (int partX = 0; partX < partsX; partX++) {
            operator.scrollXS(partX);
            operator.sleep();

            BufferedImage part = screenshot(driver);
            operator.mergePartX0(part, partX);

            if (operator.imageFull(part)) {
                operator.getGraphics().dispose();
                break;
            }
        }
        return operator.getScreenshot();
    }

    @Override
    protected Screenshot shootScrollXY(PageOptions options, PageOperator operator, WebDriver driver) {
        int partsY = operator.getPartsY();
        int partsX = operator.getPartsX();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollXY(0, partY);

            for (int partX = 0; partX < partsX; partX++) {
                operator.scrollXY(partX, partY);
                operator.sleep();

                BufferedImage part = screenshot(driver);
                operator.mergePartSS(part);

                if (operator.imageFull(part)) {
                    operator.getGraphics().dispose();
                    break;
                }
            }
        }
        return operator.getScreenshot();
    }
}