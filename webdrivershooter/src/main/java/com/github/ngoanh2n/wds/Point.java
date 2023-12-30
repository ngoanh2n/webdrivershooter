package com.github.ngoanh2n.wds;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A copy of java.awt.Point.<br><br>
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
public class Point {
    private int x;
    private int y;

    /**
     * Construct a new {@link Point} at {@code 0:0} of the coordinate.
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Construct a new {@link Point} by {@code x:y} of the coordinate.
     *
     * @param x The X coordinate of the newly constructed {@code Point}.
     * @param y The Y coordinate of the newly constructed {@code Point}.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construct a new {@link Point} by other {@link Point}.
     */
    public Point(Point other) {
        this(other.x, other.y);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get the X coordinate of this {@code Point}.
     *
     * @return The X coordinate of this {@code Point}.
     */
    public int getX() {
        return x;
    }

    /**
     * Set X coordinate for this {@code Point}.
     *
     * @param x The new X coordinate for this {@code Point}.
     */
    public Point setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Get the Y coordinate of this {@code Point}.
     *
     * @return The Y coordinate of this {@code Point}.
     */
    public int getY() {
        return y;
    }

    /**
     * Set Y coordinate for this {@code Point}.
     *
     * @param y The new X coordinate for this {@code Point}.
     */
    public Point setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Increase X coordinate for this {@code Point} by {@code value}.
     *
     * @param value The value to be added to X coordinate of this {@code Point}.
     */
    public Point incX(int value) {
        x += value;
        return this;
    }

    /**
     * Decrease X coordinate for this {@code Point} by {@code value}.
     *
     * @param value The value to be subtracted to X coordinate of this {@code Point}.
     */
    public Point decX(int value) {
        x -= value;
        return this;
    }

    /**
     * Increase Y coordinate for this {@code Point} by {@code value}.
     *
     * @param value The value to be added to Y coordinate of this {@code Point}.
     */
    public Point incY(int value) {
        y += value;
        return this;
    }

    /**
     * Decrease Y coordinate for this {@code Point} by {@code value}.
     *
     * @param value The value to be subtracted to Y coordinate of this {@code Point}.
     */
    public Point decY(int value) {
        y -= value;
        return this;
    }

    /**
     * Check whether the current {@code Point} at {@code 0:0} of the coordinate.
     *
     * @return {@code true} if the current {@code Point} at {@code 0:0} of the coordinate; {@code false} otherwise.
     */
    public boolean isOrigin() {
        return x == 0 && y == 0;
    }

    /**
     * Check whether the current {@code Point} equals to other {@code Point}.
     *
     * @param other {@code Point} to be compared.
     * @return {@code true} if the current {@code Point} equals to other {@code Point}; {@code false} otherwise.
     */
    public boolean equals(Point other) {
        return x == other.x && y == other.y;
    }

    /**
     * Returns a string representation of this {@code Point}.
     *
     * @return A string representation of this {@code Point}.
     */
    @Override
    public String toString() {
        return String.format("%d:%d", x, y);
    }
}
