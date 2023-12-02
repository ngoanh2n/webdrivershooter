package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.img.ImageComparator;
import com.github.ngoanh2n.img.ImageComparisonOptions;
import com.github.ngoanh2n.img.ImageComparisonResult;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.WebDriver;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * The result of {@link WebDriverShooter#shoot(ShooterOptions, WebDriver, ShooterOperator) WebDriverShooter.shoot(..)}.<br>
 * This contains 2 images:
 * <ul>
 *     <li>Screenshot image: {@link #getImage() Screenshot.getImage()}</li>
 *     <li>Screenshot image was masked: {@link #getMaskedImage() Screenshot.getMaskedImage()}</li>
 * </ul>
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
@ParametersAreNonnullByDefault
public class Screenshot {
    private final List<Rectangle> rects;
    private final Color maskedColor;
    private final boolean maskForRects;
    private BufferedImage image;
    private Graphics graphics;
    private boolean masked;
    private boolean updatedImage;
    private boolean updatedRects;
    private BufferedImage maskedImage;
    private Graphics maskedGraphics;

    /**
     * Construct a new {@link Screenshot}.
     *
     * @param image The image of the {@link Screenshot}.
     * @param rects The areas will be masked or ignored to be not masked.
     */
    public Screenshot(BufferedImage image, List<Rectangle> rects) {
        this(image, rects, true, Color.GRAY);
    }

    /**
     * Construct a new {@link Screenshot}.
     *
     * @param image        The image of the {@link Screenshot}.
     * @param rects        The areas will be masked or ignored to be not masked.
     * @param maskForRects Indicate to mask {@code rects} or ignored to be not masked {@code rects}.
     * @param maskedColor  The color to mask areas.
     */
    public Screenshot(BufferedImage image, List<Rectangle> rects, boolean maskForRects, Color maskedColor) {
        this.image = image;
        this.graphics = null;
        this.rects = rects;
        this.maskedColor = maskedColor;
        this.maskForRects = maskForRects;
        this.masked = false;
        this.updatedImage = false;
        this.updatedRects = false;
    }

    /**
     * Construct a new {@link Screenshot}.
     *
     * @param width        The image width of the {@link Screenshot}.
     * @param height       The image height of the {@link Screenshot}.
     * @param rects        The areas will be masked or ignored to be not masked.
     * @param maskForRects Indicate to mask {@code rects} or ignored to be not masked {@code rects}.
     * @param maskedColor  The color to mask areas.
     */
    protected Screenshot(int width, int height, List<Rectangle> rects, boolean maskForRects, Color maskedColor) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        this.graphics = image.createGraphics();
        this.rects = rects;
        this.maskedColor = maskedColor;
        this.maskForRects = maskForRects;
        this.masked = false;
        this.updatedImage = false;
        this.updatedRects = false;
    }

    //-------------------------------------------------------------------------------//

    /**
     * Save the image of the current {@link Screenshot} to the default target file.<br>
     * <ul>
     *     <li>File location: build/ngoanh2n/wds/</li>
     *     <li>File name: yyyyMMdd.HHmmss.SSS</li>
     * </ul>
     *
     * @return The target {@link File}.
     */
    public File saveImage() {
        File output = createDefaultOutput();
        return saveImage(output);
    }

    /**
     * Save the image of the current {@link Screenshot} to the specific target file.
     *
     * @param output The file to be written image to.
     * @return The target {@link File}.
     */
    public File saveImage(File output) {
        BufferedImage image = getImage();
        return saveImageToOutput(image, output);
    }

    /**
     * Save the masked image of the current {@link Screenshot} to the default target file.<br>
     * <ul>
     *     <li>File location: build/ngoanh2n/wds/</li>
     *     <li>File name: yyyyMMdd.HHmmss.SSS</li>
     * </ul>
     *
     * @return The target {@link File}.
     */
    public File saveMaskedImage() {
        File output = createDefaultOutput();
        return saveMaskedImage(output);
    }

    /**
     * Save the masked image of the current {@link Screenshot} to the specific target file.
     *
     * @param output The file to be written image to.
     * @return The target {@link File}.
     */
    public File saveMaskedImage(File output) {
        BufferedImage image = getMaskedImage();
        return saveImageToOutput(image, output);
    }

    /**
     * Get the image of the current {@link Screenshot}.
     *
     * @return The {@link BufferedImage}.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Get the masked image of the current {@link Screenshot}.
     *
     * @return The {@link BufferedImage}.
     */
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
                if (!rects.isEmpty()) {
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

    /**
     * Compare the image of the current Screenshot with other image.
     *
     * @param image The expected image.
     * @return The {@link ImageComparisonResult}.
     */
    public ImageComparisonResult compare(BufferedImage image) {
        return compare(image, ImageComparisonOptions.defaults());
    }

    /**
     * Compare the current {@link Screenshot} with other {@link Screenshot}.
     *
     * @param screenshot The expected {@link Screenshot}.
     * @return The {@link ImageComparisonResult}.
     */
    public ImageComparisonResult compare(Screenshot screenshot) {
        return compare(screenshot, ImageComparisonOptions.defaults());
    }

    /**
     * Compare the image of the current Screenshot with other image.
     *
     * @param image   The expected image.
     * @param options {@link ImageComparisonOptions} to adjust behaviors of {@link ImageComparator}.
     * @return The {@link ImageComparisonResult}.
     */
    public ImageComparisonResult compare(BufferedImage image, ImageComparisonOptions options) {
        BufferedImage act = getMaskedImage();
        return ImageComparator.compare(image, act, options);
    }

    /**
     * Compare the current {@link Screenshot} with other {@link Screenshot}.
     *
     * @param screenshot The expected {@link Screenshot}.
     * @param options    {@link ImageComparisonOptions} to adjust behaviors of {@link ImageComparator}.
     * @return The {@link ImageComparisonResult}.
     */
    public ImageComparisonResult compare(Screenshot screenshot, ImageComparisonOptions options) {
        BufferedImage exp = screenshot.getMaskedImage();
        BufferedImage act = getMaskedImage();
        return ImageComparator.compare(exp, act, options);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Disposes graphics of the current image and releases any system resources that it is using.
     */
    protected void dispose() {
        graphics.dispose();
    }

    /**
     * Draw the specified part over the current image with its top-left corner at (x,y).
     *
     * @param part The sub image to be drawn over the current image.
     * @param x    The x coordinate.
     * @param y    The Y coordinate
     */
    protected void mergePart(BufferedImage part, int x, int y) {
        graphics.drawImage(part, x, y, null);
    }

    /**
     * Update dimension of {@link BufferedImage} and its {@link Graphics}.
     *
     * @param newWidth  The new width of dimension.
     * @param newHeight The new height of dimension.
     */
    protected void updateImage(int newWidth, int newHeight) {
        if (!updatedImage) {
            image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            graphics = image.createGraphics();
            updatedImage = true;
        }
    }

    /**
     * Move all rectangles to the specified location.<br>
     * The new position is determined by moving back to the left position with distance x, moving to the top position with distance y.
     *
     * @param xToMinus The distance to move back to the left.
     * @param yToMinus The distance to move back to the top.
     */
    protected void updatedRects(int xToMinus, int yToMinus) {
        if (!updatedRects) {
            for (Rectangle rectangle : rects) {
                int newX = rectangle.getX() - xToMinus;
                int newY = rectangle.getY() - yToMinus;
                rectangle.setLocation(new Point(newX, newY));
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
        int x = rectToCut.getX();
        int y = rectToCut.getY();
        int w = rectToCut.getWidth();
        int h = rectToCut.getHeight();
        int t = BufferedImage.TYPE_INT_ARGB;

        BufferedImage area = new BufferedImage(w, h, t);
        Graphics graphics = area.getGraphics();
        graphics.drawImage(srcImage, 0, 0, w, h, x, y, w + x, h + y, null);
        graphics.dispose();
        return area;
    }

    private void drawAreaOverMaskedImage(BufferedImage area, Rectangle rectToDraw) {
        maskedGraphics.drawImage(maskedImage, 0, 0, null);
        maskedGraphics.drawImage(area, rectToDraw.getX(), rectToDraw.getY(), null);
    }

    private File createDefaultOutput() {
        String fileName = Commons.timestamp() + ".png";
        Path location = Paths.get("build/ngoanh2n/wds");
        return Commons.createDir(location).resolve(fileName).toFile();
    }

    private File saveImageToOutput(BufferedImage image, File output) {
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
