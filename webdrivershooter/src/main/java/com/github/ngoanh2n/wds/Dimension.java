package com.github.ngoanh2n.wds;

/**
 * A copy of java.awt.Dimension, avoid to load dependency on Java AWT.<br><br>
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
     * Returns a string representation of this {@code Dimension}.
     *
     * @return A string representation of this {@code Dimension}.
     */
    @Override
    public String toString() {
        return String.format("%dx%d", w, h);
    }
}
