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
 * Starting point to capture targets:
 * <ul>
 *     <li>Page: {@link WebDriverShooter#page(WebDriver...)}</li>
 *     <li>IFrame: {@link WebDriverShooter#frame(WebElement, WebDriver...)}</li>
 *     <li>Element: {@link WebDriverShooter#element(WebElement, WebDriver...)}</li>
 * </ul>
 * by strategies:
 * <ul>
 *     <li>Viewport: {@link ShooterOptions.Builder#shootViewport()}</li>
 *     <li>Vertical Scroll: {@link ShooterOptions.Builder#shootVerticalScroll()}</li>
 *     <li>Horizontal Scroll: {@link ShooterOptions.Builder#shootHorizontalScroll()}</li>
 *     <li>Full Scroll (Vertical and Horizontal): {@link ShooterOptions.Builder#shootFullScroll()}</li>
 * </ul>
 * <p>
 * Take full screenshot:
 * <ul>
 *     <li>{@link Screenshot} = {@link WebDriverShooter#page(WebDriver...)}</li>
 *     <li>{@link Screenshot} = {@link WebDriverShooter#frame(WebElement, WebDriver...)}</li>
 *     <li>{@link Screenshot} = {@link WebDriverShooter#element(WebElement, WebDriver...)}</li>
 * </ul>
 * Take full screenshot with masked elements:
 * <ul>
 *     <li>{@link Screenshot} = {@link WebDriverShooter#page(WebElement[], WebDriver...)}</li>
 *     <li>{@link Screenshot} = {@link WebDriverShooter#frame(WebElement, By[], WebDriver...)}</li>
 *     <li>{@link Screenshot} = {@link WebDriverShooter#element(WebElement, WebElement[], WebDriver...)}</li>
 * </ul>
 *
 * @author ngoanh2n
 */
public abstract class WebDriverShooter<Operator extends ShooterOperator> implements ShooterStrategy<Operator> {
    /**
     * Default constructor.
     */
    protected WebDriverShooter() { /* No implementation necessary */ }
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
    protected BufferedImage screenshot(WebDriver driver) {
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
