package com.github.ngoanh2n.wds;

/**
 * A copy of java.awt.Rectangle, avoid to load dependency on Java AWT.<br><br>
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
public class Rectangle {
    private Point p;
    private Dimension d;

    /**
     * Construct a new {@link Rectangle} by location {@code 0:0} and size {@code d}.
     *
     * @param d The size of the newly constructed {@code Rectangle}.
     */
    public Rectangle(Dimension d) {
        this(0, 0, d.getWidth(), d.getHeight());
    }

    /**
     * Construct a new {@link Rectangle} by location {@code p} and size {@code d}.
     *
     * @param p The position of the newly constructed {@code Rectangle}.
     * @param d The dimension of the newly constructed {@code Rectangle}.
     */
    public Rectangle(Point p, Dimension d) {
        this(p.getX(), p.getY(), d.getWidth(), d.getHeight());
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
        this.p = new Point(x, y);
        this.d = new Dimension(w, h);
    }

    /**
     * Get the X coordinate of this {@code Rectangle}.
     *
     * @return The X coordinate of this {@code Rectangle}.
     */
    public int getX() {
        return p.getX();
    }

    /**
     * Get the Y coordinate of this {@code Rectangle}.
     *
     * @return The Y coordinate of this {@code Rectangle}.
     */
    public int getY() {
        return p.getY();
    }

    /**
     * Get width of this {@code Rectangle}.
     *
     * @return The width of this {@code Rectangle}.
     */
    public int getWidth() {
        return d.getWidth();
    }

    /**
     * Get height of this {@code Rectangle}.
     *
     * @return The height of this {@code Rectangle}.
     */
    public int getHeight() {
        return d.getHeight();
    }

    /**
     * Get location of this {@code Rectangle}.
     *
     * @return The location of this {@code Rectangle}.
     */
    public Point getLocation() {
        return p;
    }

    /**
     * Set location for this {@code Rectangle}.
     *
     * @param p The new location for this {@code Rectangle}.
     */
    public void setLocation(Point p) {
        this.p = p;
    }

    /**
     * Get size of this {@code Rectangle}.
     *
     * @return The size of this {@code Rectangle}.
     */
    public Dimension getSize() {
        return d;
    }

    /**
     * Set size for this {@code Rectangle}.
     *
     * @param d The new size for this {@code Rectangle}.
     */
    public void setSize(Dimension d) {
        this.d = d;
    }

    /**
     * Returns a string representation of this {@code Rectangle}.
     *
     * @return A string representation of this {@code Rectangle}.
     */
    @Override
    public String toString() {
        return String.format("%s %s", p, d);
    }
}
