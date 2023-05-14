package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.image.BufferedImage;

public class ElementShooter extends WebDriverShooter<ElementOperator> {
    private final WebElement element;

    public ElementShooter(WebElement element) {
        this.element = element;
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected ElementOperator operator(ShooterOptions options, WebDriver driver) {
        return new ElementOperator(options, driver, element);
    }

    @Override
    public Screenshot shootViewport(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        operator.sleep();

        Screenshot screenshot = page(driver);
        BufferedImage image = screenshot.getImage();
        operator.mergePart00(image);

        if (operator.imageFull(image)) {
            operator.getScreenshot().dispose();
        }
        return operator.getScreenshot();
    }

    @Override
    public Screenshot shootVerticalScroll(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        int partsY = operator.getPartsY();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollSY(partY);
            operator.sleep();

            Screenshot screenshot = page(driver);
            BufferedImage image = screenshot.getImage();
            operator.mergePart0S(image);

            if (operator.imageFull(image)) {
                operator.screenshot.dispose();
                break;
            }
        }
        return operator.getScreenshot();
    }

    @Override
    public Screenshot shootHorizontalScroll(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        int partsX = operator.getPartsX();

        for (int partX = 0; partX < partsX; partX++) {
            operator.scrollXS(partX);
            operator.sleep();

            Screenshot screenshot = page(driver);
            BufferedImage part = screenshot.getImage();
            operator.mergePartS0(part);

            if (operator.imageFull(part)) {
                operator.getScreenshot().dispose();
                break;
            }
        }
        return operator.getScreenshot();
    }

    @Override
    public Screenshot shootBothDirectionScroll(ShooterOptions options, WebDriver driver, ElementOperator operator) {
        int partsY = operator.getPartsY();
        int partsX = operator.getPartsX();

        for (int partY = 0; partY < partsY; partY++) {
            operator.scrollXY(0, partY);

            for (int partX = 0; partX < partsX; partX++) {
                operator.scrollXY(partX, partY);
                operator.sleep();

                Screenshot screenshot = page(driver);
                BufferedImage part = screenshot.getImage();
                operator.mergePartSS(part);

                if (operator.imageFull(part)) {
                    operator.getScreenshot().dispose();
                    break;
                }
            }
        }
        return operator.getScreenshot();
    }
}
