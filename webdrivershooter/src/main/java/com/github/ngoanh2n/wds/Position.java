package com.github.ngoanh2n.wds;

/**
 * A copy of java.awt.Point, avoid to load dependency on Java AWT.<br><br>
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
public class Position {
    private int x;
    private int y;

    /**
     * Construct a new {@link Position} at {@code 0:0} of the coordinate.
     */
    public Position() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Construct a new {@link Position} by {@code x:y} of the coordinate.
     *
     * @param x The X coordinate of the newly constructed {@code Position}.
     * @param y The Y coordinate of the newly constructed {@code Position}.
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the X coordinate of this {@code Position}.
     *
     * @return The X coordinate of this {@code Position}.
     */
    public int getX() {
        return x;
    }

    /**
     * Set X coordinate for this {@code Position}.
     *
     * @param x The new X coordinate for this {@code Position}.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the Y coordinate of this {@code Position}.
     *
     * @return The Y coordinate of this {@code Position}.
     */
    public int getY() {
        return y;
    }

    /**
     * Set Y coordinate for this {@code Position}.
     *
     * @param y The new X coordinate for this {@code Position}.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns a string representation of this {@code Position}.
     *
     * @return A string representation of this {@code Position}.
     */
    @Override
    public String toString() {
        return String.format("%d:%d", x, y);
    }
}
