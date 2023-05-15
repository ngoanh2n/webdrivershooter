package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.util.List;

@ParametersAreNonnullByDefault
public class FrameOperator extends PageOperator {
    protected Screener framer;

    protected FrameOperator(ShooterOptions options, WebDriver driver, WebElement frame) {
        super(options, driver, frame);
    }

    //-------------------------------------------------------------------------------//

    @Override
    protected Screener createScreener(WebElement... elements) {
        WebElement frame = elements[0];
        super.createScreener().scrollElementIntoView(frame);

        framer = Screener.element(checkDPR, driver, frame);
        driver.switchTo().frame(frame);
        return Screener.page(checkDPR, driver);
    }

    @Override
    protected List<WebElement> getElements() {
        return getElements(options.locators());
    }

    @Override
    protected boolean isImageFull(BufferedImage part) {
        switch (options.shooterStrategy()) {
            case 1:
                return true;
            case 2:
                return getImageHeight() == part.getHeight(null);
            case 3:
                return getImageWidth() == part.getWidth(null);
            default:
                return getImageWidth() == part.getWidth(null) && getImageHeight() == part.getHeight(null);
        }
    }

    //-------------------------------------------------------------------------------//

    protected void mergePart00(BufferedImage part) {
        part = getFramePart(part);
        screenshot.mergePart(part, 0, 0);
    }

    @Override
    protected void mergePart0Y(BufferedImage part, int multiplierY) {
        part = getFramePart(part);
        super.mergePart0Y(part, multiplierY);
    }

    @Override
    protected void mergePartX0(BufferedImage part, int multiplierX) {
        part = getFramePart(part);
        super.mergePartX0(part, multiplierX);
    }

    @Override
    protected void mergePartSS(BufferedImage part) {
        part = getFramePart(part);
        super.mergePartSS(part);
    }

    protected BufferedImage getFramePart(BufferedImage part) {
        int x = (int) framer.getInnerRect().getX();
        int y = (int) framer.getInnerRect().getY();
        int w = (int) framer.getInnerRect().getWidth();
        int h = (int) framer.getInnerRect().getHeight();
        return part.getSubimage(x, y, w, h);
    }
}
