package com.github.ngoanh2n.wds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.ArrayList;
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

    int shooterStrategy();

    int scrollDelay();

    boolean checkDPR();

    boolean isExcepted();

    Color maskedColor();

    List<By> locators();

    List<WebElement> elements();

    //===============================================================================//

    class Builder {
        protected int shooter;
        protected int scrollDelay;
        protected boolean checkDPR;
        protected boolean isMasked;
        protected Color maskedColor;
        protected List<By> locators;
        protected List<WebElement> elements;

        protected Builder() {
            this.shooter = 4;
            this.scrollDelay = 200;
            this.checkDPR = true;
            this.isMasked = true;
            this.maskedColor = Color.GRAY;
            this.locators = new ArrayList<>();
            this.elements = new ArrayList<>();
        }

        public Builder shootViewport() {
            this.shooter = 1;
            return this;
        }

        public Builder shootVerticalScroll() {
            this.shooter = 2;
            return this;
        }

        public Builder shootHorizontalScroll() {
            this.shooter = 3;
            return this;
        }

        public Builder shootBothDirectionScroll() {
            this.shooter = 4;
            return this;
        }

        public Builder setScrollDelay(int value) {
            if (value >= 0) {
                this.scrollDelay = value;
            }
            return this;
        }

        public Builder checkDevicePixelRatio(boolean enabled) {
            this.checkDPR = enabled;
            return this;
        }

        public Builder setMaskedColor(Color color) {
            if (color != null) {
                this.maskedColor = color;
            }
            return this;
        }

        public Builder maskElements(By... locators) {
            this.isMasked = true;
            this.validateElements(locators);
            return this;
        }

        public Builder maskElements(WebElement... elements) {
            this.isMasked = true;
            this.validateElements(elements);
            return this;
        }

        public Builder maskExceptingElements(By... locators) {
            this.isMasked = false;
            this.validateElements(locators);
            return this;
        }

        public Builder ignoreExceptingElements(WebElement... elements) {
            this.isMasked = false;
            this.validateElements(elements);
            return this;
        }

        public ShooterOptions build() {
            return new Defaults(this);
        }

        private void validateElements(By... locators) {
            this.locators = Arrays.stream(locators).filter(Objects::nonNull).collect(Collectors.toList());
        }

        private void validateElements(WebElement... elements) {
            this.elements = Arrays.stream(elements).filter(Objects::nonNull).collect(Collectors.toList());
        }
    }

    //===============================================================================//

    class Defaults implements ShooterOptions {
        protected Builder builder;

        protected Defaults(Builder builder) {
            this.builder = builder;
        }

        @Override
        public int shooterStrategy() {
            return builder.shooter;
        }

        @Override
        public int scrollDelay() {
            return builder.scrollDelay;
        }

        @Override
        public boolean checkDPR() {
            return builder.checkDPR;
        }

        @Override
        public boolean isExcepted() {
            return builder.isMasked;
        }

        @Override
        public Color maskedColor() {
            return builder.maskedColor;
        }

        @Override
        public List<By> locators() {
            return builder.locators;
        }

        @Override
        public List<WebElement> elements() {
            return builder.elements;
        }
    }
}
