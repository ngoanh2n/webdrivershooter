package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.Commons;
import com.github.ngoanh2n.img.ImageComparator;
import com.github.ngoanh2n.img.ImageComparisonOptions;
import com.github.ngoanh2n.img.ImageComparisonResult;
import org.openqa.selenium.WebDriver;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private final BufferedImage image;
    private final BufferedImage maskedImage;

    /**
     * Construct a new {@link Screenshot}.
     *
     * @param image       The image of the {@link Screenshot}.
     * @param maskedImage The masked image of the {@link Screenshot}.
     */
    Screenshot(BufferedImage image, BufferedImage maskedImage) {
        this.image = image;
        this.maskedImage = maskedImage;
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

    private File createDefaultOutput() {
        String fileName = Commons.timestamp() + ".png";
        Path location = Paths.get("build/ngoanh2n/wds");
        return Commons.createDir(location).resolve(fileName).toFile();
    }
}
