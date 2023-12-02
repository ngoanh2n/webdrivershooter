package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.img.ImageComparator;
import com.github.ngoanh2n.img.ImageComparisonOptions;
import com.github.ngoanh2n.img.ImageComparisonResult;
import org.openqa.selenium.WebDriver;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
        return ImageUtils.save(image, output);
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
        return ImageUtils.save(image, output);
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
            int w = image.getWidth();
            int h = image.getHeight();
            Dimension size = new Dimension(w, h);

            maskedImage = ImageUtils.create(size);
            maskedGraphics = maskedImage.createGraphics();
            maskedGraphics.drawImage(image, 0, 0, null);

            if (maskForRects) {
                for (Rectangle rect : rects) {
                    BufferedImage maskedArea = ImageUtils.cut(image, rect);
                    ImageUtils.fill(maskedArea, maskedColor);
                    ImageUtils.drawArea(maskedImage, maskedArea, rect.getLocation());
                }
            } else {
                if (!rects.isEmpty()) {
                    ImageUtils.fill(maskedImage, maskedColor);
                    for (Rectangle rect : rects) {
                        BufferedImage area = ImageUtils.cut(image, rect);
                        ImageUtils.drawArea(maskedImage, area, rect.getLocation());
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

    private File createDefaultOutput() {
        String fileName = Commons.timestamp() + ".png";
        Path location = Paths.get("build/ngoanh2n/wds");
        return Commons.createDir(location).resolve(fileName).toFile();
    }
}
