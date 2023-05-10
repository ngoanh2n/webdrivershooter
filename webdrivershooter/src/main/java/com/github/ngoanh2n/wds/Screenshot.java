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

@ParametersAreNonnullByDefault
public class Screenshot {
    private final BufferedImage image;
    private final Rectangle[] rectangles;
    private final Color maskedColor;
    private final boolean isExcepted;
    private final boolean isMasked;
    private BufferedImage maskedImage;
    private Graphics2D maskedGraphics;

    public Screenshot(BufferedImage image, Rectangle[] rectangles, Color maskedColor, boolean isExcepted) {
        this.image = image;
        this.rectangles = rectangles;
        this.maskedColor = maskedColor;
        this.isExcepted = isExcepted;
        this.isMasked = false;
    }

    //-------------------------------------------------------------------------------//

    public File saveImage() {
        Path output = createDefaultFileOutput();
        return saveImage(output.toFile());
    }

    public File saveImage(File output) {
        return save(getImage(), output);
    }

    public File saveMaskedImage() {
        Path output = createDefaultFileOutput();
        return saveMaskedImage(output.toFile());
    }

    public File saveMaskedImage(File output) {
        return save(getMaskedImage(), output);
    }

    public BufferedImage getImage() {
        return image;
    }

    public BufferedImage getMaskedImage() {
        if (!isMasked) {
            drawMaskedImage();
            if (!isExcepted) {
                for (Rectangle rectangle : rectangles) {
                    BufferedImage elementImage = cutImage(rectangle);
                    maskImage(elementImage);
                    drawElementOverMaskedImage(elementImage, rectangle);
                }
            } else {
                if (rectangles.length > 0) {
                    maskImage(maskedImage);
                    for (Rectangle rectangle : rectangles) {
                        BufferedImage elementImage = cutImage(rectangle);
                        drawElementOverMaskedImage(elementImage, rectangle);
                    }
                }
            }
            disposeMaskedImage();
        }
        return maskedImage;
    }

    public ImageComparisonResult compare(BufferedImage image) {
        BufferedImage act = getMaskedImage();
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

    private void drawMaskedImage() {
        int w = image.getWidth();
        int h = image.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;
        maskedImage = new BufferedImage(w, h, t);
        maskedGraphics = maskedImage.createGraphics();
        maskedGraphics.drawImage(image, 0, 0, null);
    }

    private BufferedImage cutImage(Rectangle rectangle) {
        int x = (int) rectangle.getX();
        int y = (int) rectangle.getY();
        int w = (int) rectangle.getWidth();
        int h = (int) rectangle.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;

        BufferedImage eImage = new BufferedImage(w, h, t);
        Graphics graphics = eImage.getGraphics();
        graphics.drawImage(image, 0, 0, w, h, x, y, w + x, h + y, null);
        graphics.dispose();
        return eImage;
    }

    private BufferedImage maskImage(BufferedImage image) {
        Graphics2D graphics = image.createGraphics();
        graphics.setPaint(maskedColor);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.dispose();
        return image;
    }

    private void disposeMaskedImage() {
        maskedGraphics.dispose();
    }

    private void drawElementOverMaskedImage(BufferedImage elementImage, Rectangle rectangle) {
        maskedGraphics.drawImage(maskedImage, 0, 0, null);
        maskedGraphics.drawImage(elementImage, (int) rectangle.getX(), (int) rectangle.getY(), null);
    }

    private Path createDefaultFileOutput() {
        String fileName = Commons.timestamp() + ".png";
        Path location = Paths.get("build/ngoanh2n/wds");
        return Commons.createDir(location).resolve(fileName);
    }

    private File save(BufferedImage image, File output) {
        try {
            String extension = FilenameUtils.getExtension(output.getName());
            ImageIO.write(image, extension, output);
        } catch (IOException e) {
            throw new ShooterException("Error during creating ImageOutputStream");
        }
        return output;
    }
}
