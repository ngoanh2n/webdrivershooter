package com.github.ngoanh2n.wds;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A copy of java.awt.Dimension.<br><br>
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
@CanIgnoreReturnValue
@ParametersAreNonnullByDefault
public class Dimension {
    private int w;
    private int h;

    /**
     * Construct a new {@link Dimension} by {@code w}, {@code h}.
     */
    public Dimension(int w, int h) {
        this.w = w;
        this.h = h;
    }

    /**
     * Construct a new {@link Dimension} by other {@link Dimension}.
     */
    public Dimension(Dimension other) {
        this(other.w, other.h);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get width of this {@code Dimension}.
     *
     * @return The width of this {@code Dimension}.
     */
    public int getWidth() {
        return w;
    }

    /**
     * Set width for this {@code Dimension}.
     *
     * @param w The new width for this {@code Dimension}.
     */
    public void setWidth(int w) {
        this.w = w;
    }

    /**
     * Get height of this {@code Dimension}.
     *
     * @return The height of this {@code Dimension}.
     */
    public int getHeight() {
        return h;
    }

    /**
     * Set height for this {@code Dimension}.
     *
     * @param h The new height for this {@code Dimension}.
     */
    public void setHeight(int h) {
        this.h = h;
    }

    /**
     * Increase width for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be added to width of this {@code Dimension}.
     */
    public void incW(int value) {
        w += value;
    }

    /**
     * Decrease width for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be subtracted to width of this {@code Dimension}.
     */
    public void decW(int value) {
        w -= value;
    }

    /**
     * Increase height for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be added to height of this {@code Dimension}.
     */
    public void incH(int value) {
        h += value;
    }

    /**
     * Decrease height for this {@code Dimension} by {@code value}.
     *
     * @param value The value to be subtracted to height of this {@code Dimension}.
     */
    public void decH(int value) {
        h -= value;
    }

    /**
     * Check whether the current {@code Dimension} equals to other {@code Dimension}.
     *
     * @param other {@code Dimension} to be compared.
     * @return {@code true} if the current {@code Dimension} equals to other {@code Dimension}; {@code false} otherwise.
     */
    public boolean equals(Dimension other) {
        return w == other.w && h == other.h;
    }

    /**
     * Returns a string representation of this {@code Dimension}.
     *
     * @return A string representation of this {@code Dimension}.
     */
    @Override
    public String toString() {
        return String.format("%dx%d", w, h);
    }
}
