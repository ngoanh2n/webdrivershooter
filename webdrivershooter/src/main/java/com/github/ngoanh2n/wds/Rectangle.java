package com.github.ngoanh2n.wds;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A copy of java.awt.Rectangle.<br><br>
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
public class Rectangle {
    private Point point;
    private Dimension dimension;

    /**
     * Construct a new {@link Rectangle} by location {@code 0:0} and size {@code d}.
     *
     * @param dimension The size of the newly constructed {@code Rectangle}.
     */
    public Rectangle(Dimension dimension) {
        this(new Point(), dimension);
    }

    /**
     * Construct a new {@link Rectangle} by location {@code p} and size {@code d}.
     *
     * @param point     The position of the newly constructed {@code Rectangle}.
     * @param dimension The size of the newly constructed {@code Rectangle}.
     */
    public Rectangle(Point point, Dimension dimension) {
        this(point.getX(), point.getY(), dimension.getWidth(), dimension.getHeight());
    }

    /**
     * Construct a new {@link Rectangle} by location {@code x:y} and size {@code w,h}.
     *
     * @param x The X coordinate of the newly constructed {@code Point}.
     * @param y The Y coordinate of the newly constructed {@code Point}.
     * @param w The width of the newly constructed {@code Dimension}.
     * @param h The height of the newly constructed {@code Dimension}.
     */
    public Rectangle(int x, int y, int w, int h) {
        this.point = new Point(x, y);
        this.dimension = new Dimension(w, h);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get the X coordinate of this {@code Rectangle}.
     *
     * @return The X coordinate of this {@code Rectangle}.
     */
    public int getX() {
        return point.getX();
    }

    /**
     * Get the Y coordinate of this {@code Rectangle}.
     *
     * @return The Y coordinate of this {@code Rectangle}.
     */
    public int getY() {
        return point.getY();
    }

    /**
     * Get width of this {@code Rectangle}.
     *
     * @return The width of this {@code Rectangle}.
     */
    public int getWidth() {
        return dimension.getWidth();
    }

    /**
     * Get height of this {@code Rectangle}.
     *
     * @return The height of this {@code Rectangle}.
     */
    public int getHeight() {
        return dimension.getHeight();
    }

    /**
     * Get location of this {@code Rectangle}.
     *
     * @return The location of this {@code Rectangle}.
     */
    public Point getLocation() {
        return point;
    }

    /**
     * Set value for this {@code Rectangle}.
     *
     * @param value The new value for this {@code Rectangle}.
     */
    public void setLocation(Point value) {
        this.point = value;
    }

    /**
     * Get size of this {@code Rectangle}.
     *
     * @return The size of this {@code Rectangle}.
     */
    public Dimension getSize() {
        return dimension;
    }

    /**
     * Set size for this {@code Rectangle}.
     *
     * @param value The new size for this {@code Rectangle}.
     */
    public void setSize(Dimension value) {
        this.dimension = value;
    }

    /**
     * Returns a string representation of this {@code Rectangle}.
     *
     * @return A string representation of this {@code Rectangle}.
     */
    @Override
    public String toString() {
        return String.format("%s:%s %sx%s", getX(), getY(), getWidth(), getHeight());
    }
}
