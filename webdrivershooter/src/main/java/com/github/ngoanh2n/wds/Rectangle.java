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
    private Position p;
    private Dimension d;

    public Rectangle(int x, int y, int w, int h) {
        this.p = new Position(x, y);
        this.d = new Dimension(w, h);
    }

    /**
     * Get the X coordinate of this {@code Rectangle}.
     *
     * @return The X coordinate.
     */
    public int getX() {
        return p.getX();
    }

    /**
     * Get the Y coordinate of this {@code Rectangle}.
     *
     * @return The Y coordinate.
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
     * Get position of this {@code Rectangle}.
     *
     * @return The position of this {@code Rectangle}.
     */
    public Position getPosition() {
        return p;
    }

    /**
     * Set position for this {@code Rectangle}.
     *
     * @param p The new position for this {@code Rectangle}.
     */
    public void setPosition(Position p) {
        this.p = p;
    }

    /**
     * Get dimension of this {@code Rectangle}.
     *
     * @return The dimension of this {@code Rectangle}.
     */
    public Dimension getDimension() {
        return d;
    }

    /**
     * Set dimension for this {@code Rectangle}.
     *
     * @param d The new dimension for this {@code Rectangle}.
     */
    public void setDimension(Dimension d) {
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
