package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

import java.awt.*;

public interface ElementOptions extends ShooterOptions {
    static Builder builder() {
        return new ElementOptions.Builder();
    }

    static ElementOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    WebElement element();

    //===============================================================================//

    class Builder extends ShooterOptions.Builder<Builder> {
        private WebElement element;

        private Builder() {
            super();
            this.element = null;
        }

        public Builder setElement(WebElement element) {
            this.element = element;
            return this;
        }

        public ElementOptions build() {
            return new ElementOptions() {
                @Override
                public WebElement element() {
                    return element;
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
