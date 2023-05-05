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

    protected BufferedImage getFramePart(BufferedImage part) {
        int x = framer.getInnerRect().getX();
        int y = framer.getInnerRect().getY();
        int w = framer.getInnerRect().getWidth();
        int h = framer.getInnerRect().getHeight();
        return part.getSubimage(x, y, w, h);
    }
}
