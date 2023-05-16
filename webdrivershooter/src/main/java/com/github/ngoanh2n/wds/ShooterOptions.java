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

    int shooter();

    int scrollDelay();

    boolean checkDPR();

    List<By> locators();

    List<WebElement> elements();

    boolean maskForElements();

    Color maskedColor();

    //===============================================================================//

    class Builder {
        protected int shooter;
        protected int scrollDelay;
        protected boolean checkDPR;
        protected List<By> locators;
        protected List<WebElement> elements;
        protected boolean maskForElements;
        protected Color maskedColor;

        protected Builder() {
            this.shooter = 4;
            this.scrollDelay = 200;
            this.checkDPR = true;
            this.locators = new ArrayList<>();
            this.elements = new ArrayList<>();
            this.maskForElements = true;
            this.maskedColor = Color.GRAY;
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

        public Builder maskElements(By... locators) {
            this.maskForElements = true;
            this.validateElements(locators);
            return this;
        }

        public Builder maskElements(WebElement... elements) {
            this.maskForElements = true;
            this.validateElements(elements);
            return this;
        }

        public Builder maskExceptingElements(By... locators) {
            this.maskForElements = false;
            this.validateElements(locators);
            return this;
        }

        public Builder maskExceptingElements(WebElement... elements) {
            this.maskForElements = false;
            this.validateElements(elements);
            return this;
        }

        public Builder setMaskedColor(Color color) {
            if (color != null) {
                this.maskedColor = color;
            }
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
        public int shooter() {
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
        public List<By> locators() {
            return builder.locators;
        }

        @Override
        public List<WebElement> elements() {
            return builder.elements;
        }

        @Override
        public boolean maskForElements() {
            return builder.maskForElements;
        }

        @Override
        public Color maskedColor() {
            return builder.maskedColor;
        }
    }
}
