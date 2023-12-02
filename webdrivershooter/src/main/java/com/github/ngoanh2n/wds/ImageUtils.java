package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
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
    public static BufferedImage create(Dimension size) {
        int w = size.getWidth();
        int h = size.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;
        return new BufferedImage(w, h, t);
    }

    public BufferedImage fill(BufferedImage image, Color color) {
        int w = image.getWidth();
        int h = image.getHeight();

        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect(0, 0, w, h);
        graphics.dispose();
        return image;
    }

    public static BufferedImage cut(BufferedImage image, Rectangle rectToCut) {
        int x = rectToCut.getX();
        int y = rectToCut.getY();
        int w = rectToCut.getWidth();
        int h = rectToCut.getHeight();

        BufferedImage result = create(new Dimension(w, h));
        Graphics graphics = result.getGraphics();
        graphics.drawImage(image, 0, 0, w, h, x, y, w + x, h + y, null);
        graphics.dispose();
        return result;
    }

    private BufferedImage drawArea(BufferedImage image, BufferedImage area, Point location) {
        Graphics graphics = image.getGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.drawImage(area, location.getX(), location.getY(), null);
        graphics.dispose();
        return image;
    }

    public static File save(BufferedImage image, File output) {
        try {
            Commons.createDir(output);
            String extension = FilenameUtils.getExtension(output.getName());
            ImageIO.write(image, extension, output);
        } catch (IOException e) {
            throw new ShooterException("Error during creating ImageOutputStream");
        }
        return output;
    }
}
