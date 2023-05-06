package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

import java.awt.*;

public interface ShooterOptions {
    static <T extends Builder<T>> Builder<T> builder() {
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

    WebElement[] elements();

    boolean isExcepted();

    //===============================================================================//

    @SuppressWarnings("unchecked")
    class Builder<T extends Builder<T>> {
        protected boolean checkDPR;
        protected int scrollDelay;
        protected int shooterStrategy;
        protected Color maskedColor;
        protected WebElement[] elements;
        protected boolean isExcepted;

        protected Builder() {
            this.checkDPR = true;
            this.scrollDelay = 400;
            this.shooterStrategy = 4;
            this.maskedColor = Color.GRAY;
            this.elements = new WebElement[]{};
            this.isExcepted = false;
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
            this.isExcepted = false;
            this.elements = elements;
            return (T) this;
        }

        public T ignoreExceptingElements(WebElement... elements) {
            this.isExcepted = true;
            this.elements = elements;
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
        public WebElement[] elements() {
            return builder.elements;
        }

        @Override
        public boolean isExcepted() {
            return builder.isExcepted;
        }
    }
}
