package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.img.ImageComparator;
import com.github.ngoanh2n.img.ImageComparisonOptions;
import com.github.ngoanh2n.img.ImageComparisonResult;
import org.apache.commons.io.FilenameUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@ParametersAreNonnullByDefault
public class Screenshot {
    private final BufferedImage image;
    private final Graphics graphics;
    private final List<Rectangle> rects;
    private final Color maskedColor;
    private final boolean maskForRects;
    private boolean masked;
    private boolean updatedRects;
    private BufferedImage maskedImage;
    private Graphics maskedGraphics;

    public Screenshot(BufferedImage image, List<Rectangle> rects) {
        this(image, rects, false, Color.GRAY);
    }

    public Screenshot(BufferedImage image, List<Rectangle> rects, boolean maskForRects, Color maskedColor) {
        this.image = image;
        this.graphics = null;
        this.rects = rects;
        this.maskedColor = maskedColor;
        this.maskForRects = maskForRects;
        this.masked = false;
        this.updatedRects = false;
    }

    protected Screenshot(int width, int height, List<Rectangle> rects, boolean maskForRects, Color maskedColor) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.graphics = image.createGraphics();
        this.rects = rects;
        this.maskedColor = maskedColor;
        this.maskForRects = maskForRects;
        this.masked = false;
        this.updatedRects = false;
    }

    //-------------------------------------------------------------------------------//

    public File saveImage() {
        File output = createDefaultOutput();
        return saveImage(output);
    }

    public File saveImage(File output) {
        return saveImageToOutput(getImage(), output);
    }

    public File saveMaskedImage() {
        File output = createDefaultOutput();
        return saveMaskedImage(output);
    }

    public File saveMaskedImage(File output) {
        return saveImageToOutput(getMaskedImage(), output);
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getMaskedImage() {
        if (!masked) {
            initializeMaskedImage();
            if (maskForRects) {
                for (Rectangle rect : rects) {
                    BufferedImage maskedArea = cutImage(image, rect);
                    maskImage(maskedArea);
                    drawAreaOverMaskedImage(maskedArea, rect);
                }
            } else {
                if (rects.size() > 0) {
                    maskImage(maskedImage);
                    for (Rectangle rect : rects) {
                        BufferedImage area = cutImage(image, rect);
                        drawAreaOverMaskedImage(area, rect);
                    }
                }
            }
            masked = true;
            maskedGraphics.dispose();
        }
        return maskedImage;
    }

    public ImageComparisonResult compare(BufferedImage image) {
        return compare(image, ImageComparisonOptions.defaults());
    }

    public ImageComparisonResult compare(Screenshot screenshot) {
        return compare(screenshot, ImageComparisonOptions.defaults());
    }

    public ImageComparisonResult compare(BufferedImage image, ImageComparisonOptions options) {
        BufferedImage act = getMaskedImage();
        return ImageComparator.compare(image, act, options);
    }

    public ImageComparisonResult compare(Screenshot screenshot, ImageComparisonOptions options) {
        BufferedImage exp = screenshot.getMaskedImage();
        BufferedImage act = getMaskedImage();
        return ImageComparator.compare(exp, act, options);
    }

    //-------------------------------------------------------------------------------//

    protected void dispose() {
        graphics.dispose();
    }

    protected void mergePart(BufferedImage part, int x, int y) {
        graphics.drawImage(part, x, y, null);
    }

    protected void updatedRectangles(int xToMinus, int yToMinus) {
        if (!updatedRects) {
            for (Rectangle rectangle : rects) {
                int newX = rectangle.x - xToMinus;
                int newY = rectangle.y - yToMinus;
                rectangle.setLocation(newX, newY);
            }
            updatedRects = true;
        }
    }

    //-------------------------------------------------------------------------------//

    private void initializeMaskedImage() {
        int w = image.getWidth();
        int h = image.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;
        maskedImage = new BufferedImage(w, h, t);
        maskedGraphics = maskedImage.createGraphics();
        maskedGraphics.drawImage(image, 0, 0, null);
    }

    private void maskImage(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(maskedColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
    }

    private BufferedImage cutImage(BufferedImage srcImage, Rectangle rectToCut) {
        int x = (int) rectToCut.getX();
        int y = (int) rectToCut.getY();
        int w = (int) rectToCut.getWidth();
        int h = (int) rectToCut.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;

        BufferedImage area = new BufferedImage(w, h, t);
        Graphics graphics = area.getGraphics();
        graphics.drawImage(srcImage, 0, 0, w, h, x, y, w + x, h + y, null);
        graphics.dispose();
        return area;
    }

    private void drawAreaOverMaskedImage(BufferedImage area, Rectangle rectToDraw) {
        maskedGraphics.drawImage(maskedImage, 0, 0, null);
        maskedGraphics.drawImage(area, (int) rectToDraw.getX(), (int) rectToDraw.getY(), null);
    }

    private File createDefaultOutput() {
        String fileName = Commons.timestamp() + ".png";
        Path location = Paths.get("build/ngoanh2n/wds");
        return Commons.createDir(location).resolve(fileName).toFile();
    }

    private File saveImageToOutput(BufferedImage image, File output) {
        try {
            String extension = FilenameUtils.getExtension(output.getName());
            ImageIO.write(image, extension, output);
        } catch (IOException e) {
            throw new ShooterException("Error during creating ImageOutputStream");
        }
        return output;
    }
}
