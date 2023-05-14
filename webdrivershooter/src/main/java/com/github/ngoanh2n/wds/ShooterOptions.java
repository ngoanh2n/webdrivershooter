package com.github.ngoanh2n.wds;

import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface ShooterOptions {
    static Builder builder() {
        return new Builder();
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

    class Builder {
        protected boolean checkDPR;
        protected int scrollDelay;
        protected int shooterStrategy;
        protected Color maskedColor;
        protected WebElement[] elements;
        protected boolean isExcepted;

        protected Builder() {
            this.checkDPR = true;
            this.scrollDelay = 200;
            this.shooterStrategy = 4;
            this.maskedColor = Color.GRAY;
            this.elements = new WebElement[]{};
            this.isExcepted = false;
        }

        public Builder shootViewport() {
            this.shooterStrategy = 1;
            return this;
        }

        public Builder shootVerticalScroll() {
            this.shooterStrategy = 2;
            return this;
        }

        public Builder shootHorizontalScroll() {
            this.shooterStrategy = 3;
            return this;
        }

        public Builder shootBothDirectionScroll() {
            this.shooterStrategy = 4;
            return this;
        }

        public Builder checkDevicePixelRatio(boolean enabled) {
            this.checkDPR = enabled;
            return this;
        }

        public Builder setScrollDelay(int value) {
            this.scrollDelay = value;
            return this;
        }

        public Builder setMaskedColor(Color color) {
            this.maskedColor = color;
            return this;
        }

        public Builder ignoreElements(WebElement... elements) {
            this.isExcepted = false;
            this.elements = validateElements(elements);
            return this;
        }

        public Builder ignoreExceptingElements(WebElement... elements) {
            this.isExcepted = true;
            this.elements = validateElements(elements);
            return this;
        }

        public ShooterOptions build() {
            return new Defaults(this);
        }

        private static WebElement[] validateElements(WebElement... elements) {
            List<WebElement> results = Arrays.stream(elements).filter(Objects::nonNull).collect(Collectors.toList());
            return results.toArray(new WebElement[]{});
        }
    }

    //===============================================================================//

    class Defaults implements ShooterOptions {
        protected Builder builder;

        protected Defaults(Builder builder) {
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
