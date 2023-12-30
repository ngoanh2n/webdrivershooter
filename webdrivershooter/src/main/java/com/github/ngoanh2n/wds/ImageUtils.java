package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.RuntimeError;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Utils for image process.<br><br>
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
public class ImageUtils {
    /**
     * Create a new {@link BufferedImage}.
     *
     * @param size The dimension for new {@link BufferedImage}.
     * @return The {@link BufferedImage}.
     */
    public static BufferedImage create(Dimension size) {
        int w = size.getWidth();
        int h = size.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;
        return new BufferedImage(w, h, t);
    }

    /**
     * Create a new {@link BufferedImage}.
     *
     * @param image The original image to fill over new {@link BufferedImage}.
     * @return The {@link BufferedImage}.
     */
    public static BufferedImage create(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        Dimension size = new Dimension(w, h);
        return ImageUtils.create(image, size);
    }

    /**
     * Create a new {@link BufferedImage}.
     *
     * @param image The source image to fill over new {@link BufferedImage}.
     * @param size  The dimension for new {@link BufferedImage}.
     * @return The {@link BufferedImage}.
     */
    public static BufferedImage create(BufferedImage image, Dimension size) {
        BufferedImage nImage = ImageUtils.create(size);
        ImageUtils.drawArea(nImage, image, new Point(0, 0));
        return nImage;
    }

    /**
     * Fill color over image.
     *
     * @param image The image to be colored.
     * @param color The color to fill over image.
     */
    public static void fill(BufferedImage image, Color color) {
        int w = image.getWidth();
        int h = image.getHeight();
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect(0, 0, w, h);
        graphics.dispose();
    }

    /**
     * Crop a new sub image from source image by a rectangle.
     *
     * @param image The image to be cropped.
     * @param rect  The bounding rectangle against source image.
     * @return The new {@link BufferedImage} after cropped.
     */
    public static BufferedImage crop(BufferedImage image, Rectangle rect) {
        int x = rect.getX();
        int y = rect.getY();
        int w = rect.getWidth();
        int h = rect.getHeight();

        Dimension cSize = new Dimension(w, h);
        BufferedImage cImage = ImageUtils.create(cSize);
        Graphics graphics = cImage.getGraphics();
        graphics.drawImage(image, 0, 0, w, h, x, y, w + x, h + y, null);
        graphics.dispose();
        return cImage;
    }

    /**
     * Draw a sub image over source image at specific location.
     *
     * @param image    The source image to be drawed.
     * @param area     The sub image to draw over source image.
     * @param location The specific location as starting point.
     */
    public static void drawArea(BufferedImage image, BufferedImage area, Point location) {
        int x = location.getX();
        int y = location.getY();
        Graphics graphics = image.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.drawImage(area, x, y, null);
        graphics.dispose();
    }

    /**
     * Save image to output.
     *
     * @param image  The image to be saved.
     * @param output The location to store.
     * @return The saved location as {@link File}.
     */
    public static File save(BufferedImage image, File output) {
        try {
            Commons.createDir(output);
            String extension = FilenameUtils.getExtension(output.getName());
            ImageIO.write(image, extension, output);
        } catch (IOException e) {
            throw new RuntimeError("Error during creating ImageOutputStream");
        }
        return output;
    }
}
