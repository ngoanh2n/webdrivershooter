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

    boolean checkDPR();

    int scrollDelay();

    int shooterStrategy();

    Color maskedColor();

    List<By> locators();

    List<WebElement> elements();

    boolean isExcepted();

    //===============================================================================//

    class Builder {
        protected boolean checkDPR;
        protected int scrollDelay;
        protected int shooterStrategy;
        protected Color maskedColor;
        protected List<WebElement> elements;
        protected List<By> locators;
        protected boolean isExcepted;

        protected Builder() {
            this.checkDPR = true;
            this.scrollDelay = 200;
            this.shooterStrategy = 4;
            this.maskedColor = Color.GRAY;
            this.locators = new ArrayList<>();
            this.elements = new ArrayList<>();
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
            if (value >= 0) {
                this.scrollDelay = value;
            }
            return this;
        }

        public Builder setMaskedColor(Color color) {
            if (color != null) {
                this.maskedColor = color;
            }
            return this;
        }

        public Builder ignoreElements(By... locators) {
            this.isExcepted = false;
            this.validateLocators(locators);
            return this;
        }

        public Builder ignoreElements(WebElement... elements) {
            this.isExcepted = false;
            this.validateElements(elements);
            return this;
        }

        public Builder ignoreExceptingElements(By... locators) {
            this.isExcepted = true;
            this.validateLocators(locators);
            return this;
        }

        public Builder ignoreExceptingElements(WebElement... elements) {
            this.isExcepted = true;
            this.validateElements(elements);
            return this;
        }

        public ShooterOptions build() {
            return new Defaults(this);
        }

        private void validateLocators(By... locators) {
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
        public List<By> locators() {
            return builder.locators;
        }

        @Override
        public List<WebElement> elements() {
            return builder.elements;
        }

        @Override
        public boolean isExcepted() {
            return builder.isExcepted;
        }
    }
}
