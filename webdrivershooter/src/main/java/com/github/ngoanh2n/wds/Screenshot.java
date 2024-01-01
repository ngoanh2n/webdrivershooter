package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Dimension;
import com.github.ngoanh2n.Point;
import com.github.ngoanh2n.Rectangle;
import com.github.ngoanh2n.*;
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
 * The result of {@link WebDriverShooter#shoot(WebDriverShooter, ShooterOptions, WebDriver...) WebDriverShooter.shoot(..)}.<br>
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
    private final Rectangle rect;
    private final BufferedImage image;
    private final List<Rectangle> areaRects;
    private final boolean maskAreas;
    private final Color maskedColor;
    private BufferedImage mImage;

    /**
     * Construct a new {@link Screenshot}.
     *
     * @param image     The image of the {@link Screenshot}.
     * @param areaRects The area rectangles to mask or ignore to be not masked.
     */
    public Screenshot(BufferedImage image, List<Rectangle> areaRects) {
        this(image, areaRects, true, Color.GRAY);
    }

    /**
     * Construct a new {@link Screenshot}.
     *
     * @param image       The image of the {@link Screenshot}.
     * @param areaRects   The area rectangles to mask or ignore to be not masked.
     * @param maskAreas   Indicate to mask area rectangles.
     * @param maskedColor The color to mask areas.
     */
    public Screenshot(BufferedImage image, List<Rectangle> areaRects, boolean maskAreas, Color maskedColor) {
        this(new Rectangle(new Dimension(image.getWidth(), image.getHeight())), image, areaRects, maskAreas, maskedColor);
    }

    /**
     * Construct a new {@link Screenshot}.
     *
     * @param rect        The rectangle of the {@link Screenshot} against document.
     * @param image       The image of the {@link Screenshot}.
     * @param areaRects   The area rectangles to mask or ignore to be not masked.
     * @param maskAreas   Indicate to mask area rectangles.
     * @param maskedColor The color to mask areas.
     */
    public Screenshot(Rectangle rect, BufferedImage image, List<Rectangle> areaRects, boolean maskAreas, Color maskedColor) {
        this.rect = rect;
        this.image = image;
        this.areaRects = areaRects;
        this.maskAreas = maskAreas;
        this.maskedColor = maskedColor;
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get rectangle of the current {@link Screenshot}.
     *
     * @return The {@link Rectangle}.
     */
    public Rectangle getRect() {
        return rect;
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
        if (areaRects.isEmpty()) {
            return image;
        }
        if (mImage == null) {
            return maskImage();
        }
        return mImage;
    }

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

    private BufferedImage maskImage() {
        Dimension size = rect.getSize();
        mImage = ImageUtils.create(image, size);

        if (maskAreas) {
            for (Rectangle aRect : areaRects) {
                BufferedImage aImage = ImageUtils.crop(image, aRect);
                Point aLocation = aRect.getLocation();
                ImageUtils.fill(aImage, maskedColor);
                ImageUtils.drawArea(mImage, aImage, aLocation);
            }
        } else {
            if (!areaRects.isEmpty()) {
                ImageUtils.fill(mImage, maskedColor);
                for (Rectangle aRect : areaRects) {
                    Point aLocation = aRect.getLocation();
                    BufferedImage aImage = ImageUtils.crop(image, aRect);
                    ImageUtils.drawArea(mImage, aImage, aLocation);
                }
            }
        }
        return mImage;
    }

    private File createDefaultOutput() {
        String fileName = Commons.timestamp() + ".png";
        Path location = Paths.get("build/ngoanh2n/wds");
        return Commons.createDir(location).resolve(fileName).toFile();
    }
}
