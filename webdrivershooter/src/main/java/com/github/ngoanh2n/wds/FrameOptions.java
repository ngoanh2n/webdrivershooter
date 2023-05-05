package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

import java.awt.*;

public interface FrameOptions extends PageOptions {
    static Builder builder() {
        return new FrameOptions.Builder();
    }

    static FrameOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    WebElement frame();

    //===============================================================================//

    class Builder extends ShooterOptions.Builder<Builder> {
        private WebElement frame;

        private Builder() {
            super();
            this.frame = null;
        }

        public Builder setFrame(WebElement frame) {
            this.frame = frame;
            return this;
        }

        public FrameOptions build() {
            return new FrameOptions() {
                @Override
                public WebElement frame() {
                    return frame;
                }

                @Override
                public int scrollDelay() {
                    return scrollDelay;
                }

                @Override
                public int shooterStrategy() {
                    return shooterStrategy;
                }

                @Override
                public boolean checkDPR() {
                    return checkDevicePixelRatio;
                }

                @Override
                public Color decoratedColor() {
                    return decoratedColor;
                }

                @Override
                public WebElement[] ignoredElements() {
                    return ignoredElements;
                }
            };
        }
    }
}
