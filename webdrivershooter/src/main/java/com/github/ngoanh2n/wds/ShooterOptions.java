package com.github.ngoanh2n.wds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Adjust behaviors of {@link WebDriverShooter}.<br><br>
 *
 * <em>Repository:</em>
 * <ul>
 *     <li><em>GitHub: <a href="https://github.com/ngoanh2n/webdrivershooter">ngoanh2n/webdrivershooter</a></em></li>
 *     <li><em>Maven: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter">com.github.ngoanh2n:webdrivershooter</a></em></li>
 * </ul>
 *
 * @author ngoanh2n
 * @since 2021
 */
public interface ShooterOptions {
    /**
     * Get {@link Builder} class where allows to build {@link ShooterOptions}.
     *
     * @return A {@link Builder}.
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Get {@link ShooterOptions} with default options.
     *
     * @return A {@link ShooterOptions}.
     */
    static ShooterOptions defaults() {
        return builder().build();
    }

    //-------------------------------------------------------------------------------//

    /**
     * Shooting strategy is represented by a number.
     * <ul>
     *     <li>{@code 1} is Viewport</li>
     *     <li>{@code 2} is Vertical scroll</li>
     *     <li>{@code 3} is Horizontal scroll</li>
     *     <li>{@code 4} is Full scroll (vertical and horizontal)</li>
     * </ul>
     *
     * @return The number is representing for a shooting strategy.
     */
    int shooter();

    /**
     * Delay duration between scrolling times.
     *
     * @return Delay duration.
     */
    int scrollDelay();

    /**
     * Whether to check device pixel ratio.
     *
     * @return Indicate to check device pixel ratio.
     */
    boolean checkDPR();

    /**
     * The locators are masked or ignored to be not masked.
     *
     * @return The list of locators.
     */
    List<By> locators();

    /**
     * The elements are masked or ignored to be not masked.
     *
     * @return The list of locators.
     */
    List<WebElement> elements();

    /**
     * Whether to mask elements.
     * <ul>
     *     <li>{@code true}: Mask {@link ShooterOptions#locators()} if it's not empty or {@link ShooterOptions#elements()} if it's not empty.</li>
     *     <li>{@code false}: Mask screenshot excepting {@link ShooterOptions#locators()} or {@link ShooterOptions#elements()} if it's not empty.</li>
     * </ul>
     *
     * @return Indicate to mask elements and locators.
     */
    boolean maskForElements();

    /**
     * The color to mask areas.
     *
     * @return A {@link Color}.
     */
    Color maskedColor();

    //===============================================================================//

    /**
     * Build a {@link ShooterOptions}.
     */
    class Builder {
        /**
         * Shooting strategy is represented by a number
         */
        protected int shooter;
        /**
         * Delay duration between scrolling times
         */
        protected int scrollDelay;
        /**
         * Indicate to check device pixel ratio.
         */
        protected boolean checkDPR;
        /**
         * The locators are masked or ignored to be not masked.
         */
        protected List<By> locators;
        /**
         * The elements are masked or ignored to be not masked.
         */
        protected List<WebElement> elements;
        /**
         * Indicate to mask elements.
         */
        protected boolean maskForElements;
        /**
         * The color to mask areas.
         */
        protected Color maskedColor;

        /**
         * Default constructor.
         */
        protected Builder() {
            this.shooter = 4;
            this.scrollDelay = 200;
            this.checkDPR = true;
            this.locators = new ArrayList<>();
            this.elements = new ArrayList<>();
            this.maskForElements = true;
            this.maskedColor = Color.GRAY;
        }

        /**
         * Mark as taking by viewport strategy.
         *
         * @return The current {@link Builder}.
         */
        public Builder shootViewport() {
            this.shooter = 1;
            return this;
        }

        /**
         * Mark as taking by vertical scroll strategy.
         *
         * @return The current {@link Builder}.
         */
        public Builder shootVerticalScroll() {
            this.shooter = 2;
            return this;
        }

        /**
         * Mark as taking by horizontal scroll strategy.
         *
         * @return The current {@link Builder}.
         */
        public Builder shootHorizontalScroll() {
            this.shooter = 3;
            return this;
        }

        /**
         * Mark as taking by full scroll strategy.
         *
         * @return The current {@link Builder}.
         */
        public Builder shootFullScroll() {
            this.shooter = 4;
            return this;
        }

        /**
         * Set delay duration between scrolling times.
         *
         * @param value Delay duration.
         * @return The current {@link Builder}.
         */
        public Builder setScrollDelay(int value) {
            if (value >= 0) {
                this.scrollDelay = value;
            }
            return this;
        }

        /**
         * Indicate to check device pixel ratio or not.
         *
         * @param enabled Flag whether that checks or not.
         * @return The current {@link Builder}.
         */
        public Builder checkDevicePixelRatio(boolean enabled) {
            this.checkDPR = enabled;
            return this;
        }

        /**
         * Set locators to mask over screenshot.
         *
         * @param locators Locators will be masked.
         * @return The current {@link Builder}.
         */
        public Builder maskElements(By... locators) {
            this.maskForElements = true;
            this.validateElements(locators);
            return this;
        }

        /**
         * Set elements to mask over screenshot.
         *
         * @param elements Elements will be masked.
         * @return The current {@link Builder}.
         */
        public Builder maskElements(WebElement... elements) {
            this.maskForElements = true;
            this.validateElements(elements);
            return this;
        }

        /**
         * Set locators are not being masked over screenshot.
         *
         * @param locators Locators are ignored to be not masked.
         * @return The current {@link Builder}.
         */
        public Builder maskExceptingElements(By... locators) {
            this.maskForElements = false;
            this.validateElements(locators);
            return this;
        }

        /**
         * Set elements are not being masked over screenshot.
         *
         * @param elements Elements are ignored to be not masked.
         * @return The current {@link Builder}.
         */
        public Builder maskExceptingElements(WebElement... elements) {
            this.maskForElements = false;
            this.validateElements(elements);
            return this;
        }

        /**
         * Set color to mask areas.
         *
         * @param color The color to mask areas.
         * @return The current {@link Builder}.
         */
        public Builder setMaskedColor(Color color) {
            if (color != null) {
                this.maskedColor = color;
            }
            return this;
        }

        /**
         * Build {@link ShooterOptions} based on {@link Builder}.
         *
         * @return The current {@link ShooterOptions}.
         */
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

    /**
     * The default {@link ShooterOptions} implementation.
     */
    class Defaults implements ShooterOptions {
        /**
         * The {@link Builder} to build {@link ShooterOptions}.
         */
        protected Builder builder;

        /**
         * Default constructor.
         *
         * @param builder The {@link Builder} to build {@link ShooterOptions}.
         */
        protected Defaults(Builder builder) {
            this.builder = builder;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int shooter() {
            return builder.shooter;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int scrollDelay() {
            return builder.scrollDelay;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean checkDPR() {
            return builder.checkDPR;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<By> locators() {
            return builder.locators;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<WebElement> elements() {
            return builder.elements;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean maskForElements() {
            return builder.maskForElements;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Color maskedColor() {
            return builder.maskedColor;
        }
    }
}
