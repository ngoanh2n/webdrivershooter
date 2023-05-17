package com.github.ngoanh2n.wds;

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
 *     <li>Vertical scroll: {@link ShooterOptions.Builder#shootVerticalScroll()}</li>
 *     <li>Horizontal scroll: {@link ShooterOptions.Builder#shootHorizontalScroll()}</li>
 *     <li>Full scroll: {@link ShooterOptions.Builder#shootFullScroll()}</li>
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
 * @author Ho Huu Ngoan (ngoanh2n@gmail.com)
 */
public abstract class WebDriverShooter<Operator extends ShooterOperator> implements ShooterStrategy<Operator> {
    /**
     * Take full page screenshot.
     *
     * @param driver The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(WebDriver... driver) {
        ShooterOptions options = ShooterOptions.defaults();
        return WebDriverShooter.page(options, driver);
    }

    /**
     * Take full page screenshot.
     *
     * @param maskedElements Element list to be masked.
     * @param driver         The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(By[] maskedElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(maskedElements).build();
        return WebDriverShooter.page(options, driver);
    }

    /**
     * Take full page screenshot.
     *
     * @param maskedElements Element list to be masked.
     * @param driver         The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(WebElement[] maskedElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(maskedElements).build();
        return WebDriverShooter.page(options, driver);
    }

    /**
     * Take page screenshot.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param driver  The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot page(ShooterOptions options, WebDriver... driver) {
        return WebDriverShooter.shoot(new PageShooter(), options, driver);
    }

    /**
     * Take full iframe screenshot.
     *
     * @param frame  The IFrame to be captured.
     * @param driver The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
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
     * @param maskedElements Element list to be masked.
     * @param driver         The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot frame(WebElement frame, By[] maskedElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(maskedElements).build();
        return WebDriverShooter.frame(options, frame, driver);
    }

    /**
     * Take iframe screenshot.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param frame   The IFrame to be captured.
     * @param driver  The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot frame(ShooterOptions options, WebElement frame, WebDriver... driver) {
        return WebDriverShooter.shoot(new FrameShooter(frame), options, driver);
    }

    /**
     * Take full element screenshot.
     *
     * @param element The element to be captured.
     * @param driver  The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
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
     * @param maskedElements Element list to be masked.
     * @param driver         The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot element(WebElement element, By[] maskedElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(maskedElements).build();
        return WebDriverShooter.element(options, element, driver);
    }

    /**
     * Take full element screenshot.
     *
     * @param element        The element to be captured.
     * @param maskedElements Element list to be masked.
     * @param driver         The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot element(WebElement element, WebElement[] maskedElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().maskElements(maskedElements).build();
        return WebDriverShooter.element(options, element, driver);
    }

    /**
     * Take element screenshot.
     *
     * @param options {@link ShooterOptions} to adjust behaviors of {@link WebDriverShooter}.
     * @param element The element to be captured.
     * @param driver  The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The {@link Screenshot}.
     */
    public static Screenshot element(ShooterOptions options, WebElement element, WebDriver... driver) {
        return WebDriverShooter.shoot(new ElementShooter(element), options, driver);
    }

    //-------------------------------------------------------------------------------//

    /**
     * Get the current {@link WebDriver}.
     *
     * @param args The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
     * @return The current {@link WebDriver}.
     */
    protected static WebDriver getDriver(WebDriver... args) {
        if (args.length != 0) {
            if (args[0] != null) {
                return args[0];
            }
            throw new ShooterException("You have passed a nullable WebDriver");
        } else {
            ServiceLoader<WebDriverProvider> serviceLoader = ServiceLoader.load(WebDriverProvider.class);
            Iterator<WebDriverProvider> serviceLoaders = serviceLoader.iterator();

            if (serviceLoaders.hasNext()) {
                return serviceLoaders.next().provide();
            }
            throw new ShooterException("You have not implemented WebDriverProvider");
        }
    }

    /**
     * Take screenshot by a specific shooting strategy.
     *
     * @param shooter    A {@link WebDriverShooter} implementation.
     * @param options    Options to adjust {@link WebDriverShooter} by your expectation.
     * @param args       The current {@link WebDriver}. Only the first {@link WebDriver} of driver array is used.
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
