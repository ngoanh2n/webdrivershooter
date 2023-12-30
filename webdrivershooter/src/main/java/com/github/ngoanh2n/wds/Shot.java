package com.github.ngoanh2n.wds;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;

/**
 * Represent a screenshot image and its rectangle.<br><br>
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
public class Shot {
    private final Position position;
    private Rectangle rect;
    private BufferedImage image;

    /**
     * Construct a new {@link Shot}.
     *
     * @param image The current image.
     * @param rect  The rectangle of the image against document.
     */
    public Shot(BufferedImage image, Rectangle rect) {
        this.image = image;
        this.rect = rect;
        this.position = new Position();
    }

    /**
     * Construct a new {@link Shot}.
     *
     * @param image    The current image.
     * @param rect     The rectangle of the image against document.
     * @param position The position of the image against document.
     */
    public Shot(BufferedImage image, Rectangle rect, Position position) {
        this.image = image;
        this.rect = rect;
        this.position = position;
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get rectangle of this {@code Shot}.
     *
     * @return The rectangle of this {@code Shot}.
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Set rectangle of this {@code Shot}.
     *
     * @param rect The new rectangle for this {@code Shot}.
     */
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Get screenshot image of this {@code Shot}.
     *
     * @return The screenshot image of this {@code Shot}.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Set screenshot image of this {@code Shot}.
     *
     * @param image The new screenshot image for this {@code Shot}.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Get screenshot position of this {@code Shot}.
     *
     * @return The screenshot position of this {@code Shot}.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns a string to represent rectangle of this {@code Shot}.
     *
     * @return A string to represent rectangle of this {@code Shot}.
     */
    @Override
    public String toString() {
        return String.format("%s %s", position, rect);
    }

    /**
     * Represents positions of a {@link Shot}.
     * <br>
     * XSYS XMYS XMYS XLYS <br>
     * XSYM XMYM XMYM XLYM <br>
     * XSYM XMYM XMYM XLYM <br>
     * XSYL XMYM XMYM XLYM <br>
     */
    public static class Position {
        private final Type x;
        private final Type y;

        /**
         * Construct a new {@link Position}.
         */
        public Position() {
            this(Type.S, Type.S);
        }

        /**
         * Construct a new {@link Position}.
         *
         * @param x Type by vertical direction.
         * @param y Type by horizontal direction.
         */
        public Position(Type x, Type y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Get type by horizontal direction.
         *
         * @return The type of horizontal direction.
         */
        public Type getX() {
            return x;
        }

        /**
         * Get type by vertical direction.
         *
         * @return The type of vertical direction.
         */
        public Type getY() {
            return y;
        }

        /**
         * Check whether the current {@code Shot} at {@code S:S} of the position.
         *
         * @return {@code true} if the current {@code Shot} at {@code S:S} of the position; {@code false} otherwise.
         */
        public boolean isOrigin() {
            return x == Type.S && y == Type.S;
        }

        /**
         * Returns a string representation of this {@code Shot}.
         *
         * @return A string representation of this {@code Shot}.
         */
        public String toString() {
            return "X" + x.name() + "Y" + y.name();
        }

        /**
         * Type of {@link Position}.
         * <ul>
         *     <li>{@link Type#S}: Start</li>
         *     <li>{@link Type#M}: Middle</li>
         *     <li>{@link Type#L}: Last</li>
         *     <li>{@link Type#U}: Unknown</li>
         * </ul>
         */
        public enum Type {
            U, S, M, L
        }
    }
}
