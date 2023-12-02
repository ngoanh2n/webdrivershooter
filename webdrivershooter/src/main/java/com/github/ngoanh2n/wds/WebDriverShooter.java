package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.wdc.WebDriverChecker;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Starting point to take screenshot.<br><br>
 *
 * <b>Target:</b>
 * <ul>
 *     <li>{@link WebDriverShooter#page(WebDriver...) WebDriverShooter.page(..)}</li>
 *     <li>{@link WebDriverShooter#frame(WebElement, WebDriver...) WebDriverShooter.frame(..)}</li>
 *     <li>{@link WebDriverShooter#element(WebElement, WebDriver...) WebDriverShooter.element(..)}</li>
 * </ul>
 *
 * <b>Strategy:</b>
 * <ul>
 *     <li>{@link ShooterOptions.Builder#shootViewport() ShooterOptions.builder().shootViewport()}</li>
 *     <li>{@link ShooterOptions.Builder#shootVerticalScroll() ShooterOptions.builder().shootVerticalScroll()}</li>
 *     <li>{@link ShooterOptions.Builder#shootHorizontalScroll() ShooterOptions.builder().shootHorizontalScroll()}</li>
 *     <li>{@link ShooterOptions.Builder#shootFullScroll() ShooterOptions.builder().shootFullScroll()}</li>
 * </ul>
 *
 * <b>Screenshot</b><br>
 * Below API methods are using default {@link ShooterOptions} with full screenshot.
 * <ul>
 *      <li>{@code Screenshot screenshot = WebDriverShooter.page(driver)}</li>
 *      <li>{@code Screenshot screenshot = WebDriverShooter.frame(frame, driver)}</li>
 *      <li>{@code Screenshot screenshot = WebDriverShooter.element(element, driver)}</li>
 * </ul>
 *
 * <b>Masking</b><br>
 * When taking the {@code iframe}, you have to pass locators instead.
 * <ul>
 *      <li>{@code Screenshot screenshot = WebDriverShooter.page(elementsToMask, driver)}</li>
 *      <li>{@code Screenshot screenshot = WebDriverShooter.frame(frame, locatorsToMask, driver)}</li>
 *      <li>{@code Screenshot screenshot = WebDriverShooter.element(element, elementsToMask, driver)}</li>
 * </ul>
 *
 * <b>Screenshot and mask with customized {@link ShooterOptions}</b><br>
 * <ul>
 *     <li>Mask elements
 *         <pre>{@code
 *              ShooterOptions options = ShooterOptions
 *                      .builder()
 *                      .maskElements(elements)
 *                      .build();
 *              Screenshot screenshot = WebDriverShooter.page(options, driver);
 *         }</pre>
 *     </li>
 *     <li>Mask all excepting elements
 *         <pre>{@code
 *              ShooterOptions options = ShooterOptions
 *                      .builder()
 *                      .maskExceptingElements(elements)
 *                      .build();
 *              Screenshot screenshot = WebDriverShooter.page(options, driver);
 *         }</pre>
 *     </li>
 * </ul>
 *
 * <b>Comparison</b>
 * <ul>
 *     <li>With the image
 *          <pre>{@code
 *               Screenshot screenshot = WebDriverShooter.page(driver);
 *               ImageComparisonResult result = screenshot.compare(image);
 *          }</pre>
 *     </li>
 *     <li>With the screenshot
 *          <pre>{@code
 *               driver.get(URL1);
 *               Screenshot screenshot1 = WebDriverShooter.page(driver);
 *
 *               driver.get(URL2);
 *               Screenshot screenshot2 = WebDriverShooter.page(driver);
 *
 *               ImageComparisonResult result = screenshot1.compare(screenshot2);
 *               Assertions.assertTrue(result.isDifferent());
 *          }</pre>
 *     </li>
 * </ul>
 *
 * <b>Extension</b><br>
 * <ul>
 *     <li><em>Selenide: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter-selenide">com.github.ngoanh2n:webdrivershooter-selenide</a></em></li>
 *     <li><em>JUnit5: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter-junit5">com.github.ngoanh2n:webdrivershooter-junit5</a></em></li>
 *     <li><em>TestNG: <a href="https://mvnrepository.com/artifact/com.github.ngoanh2n/webdrivershooter-testng">com.github.ngoanh2n:webdrivershooter-testng</a></em></li>
 * </ul>
 * It automatically provides the current {@link WebDriver} instance to {@link WebDriverShooter}.<br>
 * You don't need to pass the {@link WebDriver} instance to the argument of shooting methods.<br>
 * <ul>
 *     <li>With extension <pre>{@code WebDriverShooter.page()}</pre></li>
 *     <li>Without extension <pre>{@code WebDriverShooter.page(driver)}</pre></li>
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
public abstract class WebDriverShooter<Operator extends ShooterOperator> implements ShooterStrategy<Operator> {
    /**
     * Default constructor.
     */
    protected WebDriverShooter() { /**/ }

    /**
     * Take full page screenshot.
     *
     * @param driver The {@link WebDriver} for the first argument, and can be empty.<br>
     *               {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(WebDriver... driver) {
        ShooterOptions options = ShooterOptions.defaults();
        return WebDriverShooter.page(options, driver);
    }

    /**
     * Take full page screenshot.
     *
     * @param locatorsToMask Element list to be masked.
     * @param driver         The {@link WebDriver} for the first argument, and can be empty.<br>
     *                       {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(By[] locatorsToMask, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(locatorsToMask).build();
        return WebDriverShooter.page(options, driver);
    }

    /**
     * Take full page screenshot.
     *
     * @param elementsToMask Element list to be masked.
     * @param driver         The {@link WebDriver} for the first argument, and can be empty.<br>
     *                       {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(WebElement[] elementsToMask, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(elementsToMask).build();
        return WebDriverShooter.page(options, driver);
    }

    /**
     * Take page screenshot.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The {@link WebDriver} for the first argument, and can be empty.<br>
     *                {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(ShooterOptions options, WebDriver... driver) {
        return WebDriverShooter.shoot(new PageShooter(), options, driver);
    }

    /**
     * Take full iframe screenshot.
     *
     * @param frame  The IFrame to be captured.
     * @param driver The {@link WebDriver} for the first argument, and can be empty.<br>
     *               {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot frame(WebElement frame, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().build();
        return WebDriverShooter.frame(options, frame, driver);
    }

    /**
     * Take full iframe screenshot.
     *
     * @param frame          The IFrame to be captured.
     * @param locatorsToMask Element list to be masked.
     * @param driver         The {@link WebDriver} for the first argument, and can be empty.<br>
     *                       {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot frame(WebElement frame, By[] locatorsToMask, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(locatorsToMask).build();
        return WebDriverShooter.frame(options, frame, driver);
    }

    /**
     * Take iframe screenshot.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param frame   The IFrame to be captured.
     * @param driver  The {@link WebDriver} for the first argument, and can be empty.<br>
     *                {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot frame(ShooterOptions options, WebElement frame, WebDriver... driver) {
        return WebDriverShooter.shoot(new FrameShooter(frame), options, driver);
    }

    /**
     * Take full element screenshot.
     *
     * @param element The element to be captured.
     * @param driver  The {@link WebDriver} for the first argument, and can be empty.<br>
     *                {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot element(WebElement element, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().build();
        return WebDriverShooter.element(options, element, driver);
    }

    /**
     * Take full element screenshot.
     *
     * @param element        The element to be captured.
     * @param locatorsToMask Element list to be masked.
     * @param driver         The {@link WebDriver} for the first argument, and can be empty.<br>
     *                       {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot element(WebElement element, By[] locatorsToMask, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(locatorsToMask).build();
        return WebDriverShooter.element(options, element, driver);
    }

    /**
     * Take full element screenshot.
     *
     * @param element        The element to be captured.
     * @param elementsToMask Element list to be masked.
     * @param driver         The {@link WebDriver} for the first argument, and can be empty.<br>
     *                       {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot element(WebElement element, WebElement[] elementsToMask, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(elementsToMask).build();
        return WebDriverShooter.element(options, element, driver);
    }

    /**
     * Take element screenshot.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param element The element to be captured.
     * @param driver  The {@link WebDriver} for the first argument, and can be empty.<br>
     *                {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The {@link Screenshot}.
     */
    public static Screenshot element(ShooterOptions options, WebElement element, WebDriver... driver) {
        return WebDriverShooter.shoot(new ElementShooter(element), options, driver);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get the current {@link WebDriver}.
     *
     * @param args The {@link WebDriver} for the first argument, and can be empty.<br>
     *             {@link WebDriverShooter} doesn't care from the second argument onwards.
     * @return The current {@link WebDriver}.
     */
    protected static WebDriver getDriver(WebDriver... args) {
        if (args.length != 0) {
            if (args[0] != null) {
                return args[0];
            }
            throw new ShooterException.NullDriverPassed();
        } else {
            ServiceLoader<WebDriverProvider> serviceLoader = ServiceLoader.load(WebDriverProvider.class);
            Iterator<WebDriverProvider> serviceLoaders = serviceLoader.iterator();

            if (serviceLoaders.hasNext()) {
                WebDriverProvider provider = serviceLoaders.next();
                WebDriver driver = provider.provide();

                if (driver == null) {
                    throw new ShooterException.NullDriverProvided();
                }
                if (!WebDriverChecker.isAlive(driver)) {
                    throw new ShooterException.ClosedDriverProvided();
                }
                return driver;
            }
        }
        throw new ShooterException.NoneDriver();
    }

    /**
     * Take screenshot by a specific shooting strategy.
     *
     * @param shooter    A {@link WebDriverShooter} implementation.
     * @param options    {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param args       The {@link WebDriver} for the first argument, and can be empty.<br>
     *                   {@link ShooterOperator} doesn't care from the second argument onwards.
     * @param <Operator> A {@link ShooterOperator} implementation.
     * @return The {@link Screenshot}.
     */
    protected static <Operator extends ShooterOperator> Screenshot shoot(WebDriverShooter<Operator> shooter, ShooterOptions options, WebDriver... args) {
        WebDriver driver = getDriver(args);
        Operator operator = shooter.operator(options, driver);
        return shooter.shoot(options, driver, operator);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Take viewport screenshot by using {@link TakesScreenshot#getScreenshotAs(OutputType)}.
     *
     * @param driver The current {@link WebDriver}.
     * @return The {@link BufferedImage}.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    protected BufferedImage shoot(WebDriver driver) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new ShooterException(e);
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    //-------------------------------------------------------------------------------//

    /**
     * Provide a {@link ShooterOperator} implementation.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}.
     * @return The {@link ShooterOperator}.
     */
    protected abstract Operator operator(ShooterOptions options, WebDriver driver);
}
