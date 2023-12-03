package com.github.ngoanh2n.wds;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Operate coordinates and rectangles on screen.<br><br>
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
public abstract class ShooterOperator {
    /**
     * {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}
     */
    protected ShooterOptions options;
    /**
     * The current {@link WebDriver}.
     */
    protected WebDriver driver;
    /**
     * Indicate to check device pixel ratio.
     */
    protected boolean checkDPR;
    /**
     * Operate coordinates on screen.
     */
    protected Screener screener;
    /**
     * Merge shot parts and mask rectangles.
     */
    protected ShotImage shotImage;

    /**
     * Construct a new {@link ShooterOperator}.
     *
     * @param options  {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver   The current {@link WebDriver}.
     * @param elements The element to support {@link #createScreener(WebElement...)}.<br>
     *                 Only the first {@link WebElement} of element array is used. May be empty.
     */
    protected ShooterOperator(ShooterOptions options, WebDriver driver, WebElement... elements) {
        this.options = options;
        this.driver = driver;
        this.checkDPR = options.checkDPR();
        this.screener = createScreener(elements);
        this.shotImage = createShotImage(options);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Create a {@link Screener}.
     *
     * @param elements The element to support {@link Screener} when interacting a element.
     * @return The {@link Screener}.
     */
    protected Screener createScreener(WebElement... elements) {
        return new Screener(checkDPR, driver);
    }

    /**
     * Create a {@link ShotImage}.
     *
     * @return The {@link ShotImage}.
     */
    protected ShotImage createShotImage(ShooterOptions options) {
        int width = getShotImageWidth();
        int height = getShotImageHeight();
        Dimension size = new Dimension(width, height);
        List<WebElement> elements = getElements();
        List<Rectangle> rectangles = getRectangles(elements);
        return new ShotImage(options, size, rectangles);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get elements to mask or ignore to be not masked.
     *
     * @return The {@link WebElement} list.
     */
    protected List<WebElement> getElements() {
        if (!options.elements().isEmpty()) {
            return options.elements();
        }
        return getElements(options.locators());
    }

    /**
     * Get elements to mask or ignore to be not masked.
     *
     * @param locators List of {@link By} to mask or ignore to be not masked.
     * @return The {@link By} list.
     */
    protected List<WebElement> getElements(List<By> locators) {
        List<WebElement> elements = new ArrayList<>();
        for (By locator : locators) {
            WebElement element = driver.findElement(locator);
            elements.add(element);
        }
        return elements;
    }

    /**
     * Get areas to mask or ignore to be not masked.
     *
     * @param elements The list of {@link WebElement} to mask or ignore to be not masked.
     * @return The {@link Rectangle} list.
     */
    protected List<Rectangle> getRectangles(List<WebElement> elements) {
        List<Rectangle> rectangles = new ArrayList<>();
        for (WebElement element : elements) {
            int x = (int) (screener.getDPR() * element.getLocation().getX());
            int y = (int) (screener.getDPR() * element.getLocation().getY());
            int w = (int) (screener.getDPR() * element.getSize().getWidth());
            int h = (int) (screener.getDPR() * element.getSize().getHeight());

            Point location = new Point(x, y);
            Dimension size = new Dimension(w, h);
            rectangles.add(new Rectangle(location, size));
        }
        return rectangles;
    }

    /**
     * Get the width of image for creating the {@link Screenshot}.
     *
     * @return The width of image.
     */
    protected int getShotImageWidth() {
        return switch (options.shooter()) {
            case 1, 2 -> screener.getInnerRect().getWidth();
            default -> screener.getOuterRect().getWidth();
        };
    }

    /**
     * Get the height of image for creating the {@link Screenshot}.
     *
     * @return The height of image.
     */
    protected int getShotImageHeight() {
        return switch (options.shooter()) {
            case 1, 3 -> screener.getInnerRect().getHeight();
            default -> screener.getOuterRect().getHeight();
        };
    }

    /**
     * Whether to check the screenshot image is merged fully.
     *
     * @param part The current screenshot part.
     * @return Indicate the image is merged fully.
     */
    protected boolean isImageFull(BufferedImage part) {
        return switch (options.shooter()) {
            case 1, 2 -> getShotImageHeight() == part.getHeight(null);
            case 3 -> getShotImageWidth() == part.getWidth(null);
            default -> getShotImageWidth() == part.getWidth(null) && getShotImageHeight() == part.getHeight(null);
        };
    }

    //-------------------------------------------------------------------------------//

    /**
     * Make the current thread to sleep with {@link ShooterOptions#scrollDelay()} ms.
     */
    protected void sleep() {
        try {
            Thread.sleep(options.scrollDelay());
        } catch (InterruptedException e) {
            throw new ShooterException(e);
        }
    }

    /**
     * Get the number of times are scrolled vertically.
     *
     * @return The number of screenshot parts need to merge into the screenshot image by vertically.
     */
    protected int getPartsY() {
        int outerH = screener.getOuterRect().getHeight();
        int innerH = screener.getInnerRect().getHeight();
        return (int) Math.ceil(((double) outerH) / innerH);
    }

    /**
     * Get the number of times are scrolled horizontally.
     *
     * @return The number of screenshot parts need to merge into the screenshot image by horizontally.
     */
    protected int getPartsX() {
        int outerW = screener.getOuterRect().getWidth();
        int innerW = screener.getInnerRect().getWidth();
        return (int) Math.ceil(((double) outerW) / innerW);
    }

    /**
     * Get the result after {@link WebDriverShooter#shoot(ShooterOptions, WebDriver, ShooterOperator)}.
     *
     * @return The {@link Screenshot}.
     */
    protected Screenshot getScreenshot() {
        ImmutablePair<BufferedImage, BufferedImage> result = shotImage.getResult();
        BufferedImage image = result.getLeft();
        BufferedImage maskedImage = result.getRight();
        return new Screenshot(image, maskedImage);
    }
}
