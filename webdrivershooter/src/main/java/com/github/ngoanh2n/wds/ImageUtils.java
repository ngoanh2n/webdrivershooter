package com.github.ngoanh2n.wds;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    public static BufferedImage cut(BufferedImage srcImage, Rectangle rectToCut) {
        int x = rectToCut.getX();
        int y = rectToCut.getY();
        int w = rectToCut.getWidth();
        int h = rectToCut.getHeight();

        BufferedImage image = create(new Dimension(w, h));
        Graphics graphics = image.getGraphics();
        graphics.drawImage(srcImage, 0, 0, w, h, x, y, w + x, h + y, null);
        graphics.dispose();
        return image;
    }
}
