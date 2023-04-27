package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

import java.awt.*;

public interface PageOptions extends ShooterOptions {
    static Builder builder() {
        return new PageOptions.Builder();
    }

    static PageOptions defaults() {
        return builder().build();
    }

    //===============================================================================//

    class Builder extends ShooterOptions.Builder<Builder> {
        private Builder() {
            super();
        }

        public PageOptions build() {
            return new PageOptions() {
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
                public WebElement ignoredElement() {
                    return ignoredElement;
                }
            };
        }
    }
}
