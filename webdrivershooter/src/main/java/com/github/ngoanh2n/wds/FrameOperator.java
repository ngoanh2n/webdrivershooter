package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;

@ParametersAreNonnullByDefault
public abstract class FrameOperator extends PageOperator {
    protected WebElement frame;
    protected Screener framer;

    protected FrameOperator(FrameOptions options, WebDriver driver) {
        super(options, driver);
        this.frame = options.frame();
        this.screener.scrollElementIntoView(frame);

        boolean checkDPR = options.checkDPR();
        this.framer = Screener.element(checkDPR, driver, frame);
        this.driver.switchTo().frame(frame);
        this.screener = Screener.page(checkDPR, driver);
    }

    //-------------------------------------------------------------------------------//

    protected void mergePart00(BufferedImage part) {
        part = getFramePart(part);
        getGraphics().drawImage(part, 0, 0, null);
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

    //-------------------------------------------------------------------------------//

    protected BufferedImage getFramePart(BufferedImage part) {
        int x = (int) framer.getInnerRect().getX();
        int y = (int) framer.getInnerRect().getY();
        int w = (int) framer.getInnerRect().getWidth();
        int h = (int) framer.getInnerRect().getHeight();
        return part.getSubimage(x, y, w, h);
    }
}
