package com.github.ngoanh2n.wds;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Merge shot parts and mask rectangles.<br><br>
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
public class ShotImage {
    protected final static Logger log = LoggerFactory.getLogger(ShotImage.class);
    private final ShooterOptions options;
    private final List<Rectangle> rectangles;
    private final LinkedList<Rectangle> parts;
    private BufferedImage image;

    protected ShotImage(ShooterOptions options, Dimension size, List<Rectangle> rectangles) {
        this.options = options;
        this.rectangles = rectangles;
        this.parts = new LinkedList<>();
        this.image = ImageUtils.create(size);
        log.info(String.valueOf(size));
    }

    //-------------------------------------------------------------------------------//

    protected void merge(BufferedImage shot, Point location) {
        checkSizeForViewport(shot);
        drawShotImage(shot, location);
        collectShotRect(shot, location);
        log.debug("Shot -> " + parts.getLast());
    }

    protected ImmutablePair<BufferedImage, BufferedImage> getResult() {
        BufferedImage maskedImage = getMaskedImage();
        return new ImmutablePair<>(image, maskedImage);
    }

    //-------------------------------------------------------------------------------//

    private int getLeft(Point location) {
        return image.getHeight() - location.getY();
    }

    private void checkSizeForViewport(BufferedImage shot) {
        if (options.shooter() == 1) {
            int w = shot.getWidth();
            int h = shot.getHeight();
            Dimension size = new Dimension(w, h);
            image = ImageUtils.create(size);
        }
    }

    private void drawShotImage(BufferedImage shot, Point location) {
        int x = location.getX();
        int y = location.getY();

        if (isTheLastShot(shot, location)) {
            int left = getLeft(location);
            y = y - shot.getHeight() + left;
        }

        Graphics graphics = image.createGraphics();
        graphics.drawImage(shot, x, y, null);
        graphics.dispose();
    }

    private void collectShotRect(BufferedImage shot, Point location) {
        int x = location.getX();
        int y = location.getY();
        int w = shot.getWidth();
        int h = shot.getHeight();

        if (isTheLastShot(shot, location)) {
            h = getLeft(location);
        }

        Rectangle part = new Rectangle(x, y, w, h);
        parts.add(part);
    }

    private boolean isTheLastShot(BufferedImage shot, Point location) {
        int left = getLeft(location);
        return left < shot.getHeight();
    }

    //-------------------------------------------------------------------------------//

    private BufferedImage getMaskedImage() {
        int w = image.getWidth();
        int h = image.getHeight();
        Dimension size = new Dimension(w, h);

        BufferedImage maskedImage = ImageUtils.create(size);
        Graphics maskedGraphics = maskedImage.createGraphics();
        maskedGraphics.drawImage(image, 0, 0, null);

        if (options.maskForElements()) {
            for (Rectangle rectangle : rectangles) {
                BufferedImage maskedArea = ImageUtils.cut(image, rectangle);
                ImageUtils.fill(maskedArea, options.maskedColor());
                ImageUtils.drawArea(maskedImage, maskedArea, rectangle.getLocation());
            }
        } else {
            if (!rectangles.isEmpty()) {
                ImageUtils.fill(maskedImage, options.maskedColor());
                for (Rectangle rectangle : rectangles) {
                    BufferedImage area = ImageUtils.cut(image, rectangle);
                    ImageUtils.drawArea(maskedImage, area, rectangle.getLocation());
                }
            }
        }

        maskedGraphics.dispose();
        return maskedImage;
    }
}
