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

    boolean checkDPR();

    int scrollDelay();

    int shooterStrategy();

    Color maskedColor();

    WebElement[] maskedElements();

    //===============================================================================//

    @SuppressWarnings("unchecked")
    class Builder<T extends Builder<T>> {
        protected boolean checkDPR;
        protected int scrollDelay;
        protected int shooterStrategy;
        protected Color maskedColor;
        protected WebElement[] maskedElements;

        protected Builder() {
            this.checkDPR = true;
            this.scrollDelay = 400;
            this.shooterStrategy = 4;
            this.maskedColor = Color.GRAY;
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

        public T checkDevicePixelRatio(boolean enabled) {
            this.checkDPR = enabled;
            return (T) this;
        }

        public T setScrollDelay(int value) {
            this.scrollDelay = value;
            return (T) this;
        }

        public T setMaskedColor(Color color) {
            this.maskedColor = color;
            return (T) this;
        }

        public T ignoreElements(WebElement... elements) {
            this.maskedElements = elements;
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
        public boolean checkDPR() {
            return builder.checkDPR;
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
        public Color maskedColor() {
            return builder.maskedColor;
        }

        @Override
        public WebElement[] maskedElements() {
            return builder.maskedElements;
        }
    }
}
