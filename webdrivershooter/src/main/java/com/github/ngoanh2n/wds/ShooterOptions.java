package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

import java.awt.*;

public interface ShooterOptions {
    static Builder<?> builder() {
        return new Builder<>();
    }

    static ShooterOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    int scrollDelay();

    int shooterStrategy();

    boolean checkDPR();

    Color decoratedColor();

    WebElement[] ignoredElements();

    //===============================================================================//

    @SuppressWarnings("unchecked")
    class Builder<T extends Builder<T>> {
        protected int scrollDelay;
        protected int shooterStrategy;
        protected boolean checkDPR;
        protected Color decoratedColor;
        protected WebElement[] ignoredElements;

        protected Builder() {
            this.scrollDelay = 400;
            this.shooterStrategy = 4;
            this.checkDPR = true;
            this.decoratedColor = Color.GRAY;
        }

        public T shootViewport() {
            this.shooterStrategy = 1;
            return (T) this;
        }

        public T shootVerticalScroll() {
            this.shooterStrategy = 2;
            return (T) this;
        }

        public T shootHorizontalScroll() {
            this.shooterStrategy = 3;
            return (T) this;
        }

        public T shootBothDirectionScroll() {
            this.shooterStrategy = 4;
            return (T) this;
        }

        public T setScrollDelay(int value) {
            this.scrollDelay = value;
            return (T) this;
        }

        public T checkDevicePixelRatio(boolean enabled) {
            this.checkDPR = enabled;
            return (T) this;
        }

        public T ignoreElements(WebElement... elements) {
            this.ignoredElements = elements;
            return (T) this;
        }

        public ShooterOptions build() {
            return new Defaults(this);
        }
    }

    //===============================================================================//

    class Defaults implements ShooterOptions {
        protected Builder<?> builder;

        protected Defaults(Builder<?> builder) {
            this.builder = builder;
        }

        @Override
        public int scrollDelay() {
            return builder.scrollDelay;
        }

        @Override
        public int shooterStrategy() {
            return builder.shooterStrategy;
        }

        @Override
        public boolean checkDPR() {
            return builder.checkDPR;
        }

        @Override
        public Color decoratedColor() {
            return builder.decoratedColor;
        }

        @Override
        public WebElement[] ignoredElements() {
            return builder.ignoredElements;
        }
    }
}
