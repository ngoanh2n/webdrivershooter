package com.github.ngoanh2n.wds;

import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

public abstract class WebDriverShooter<Operator extends ShooterOperator> implements ShooterStrategy<Operator> {
    public static Screenshot page(WebDriver... driver) {
        ShooterOptions options = ShooterOptions.defaults();
        return WebDriverShooter.page(options, driver);
    }

    public static Screenshot page(By[] ignoredLocators, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().ignoreElements(ignoredLocators).build();
        return WebDriverShooter.page(options, driver);
    }

    public static Screenshot page(WebElement[] ignoredElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().ignoreElements(ignoredElements).build();
        return WebDriverShooter.page(options, driver);
    }

    public static Screenshot page(ShooterOptions options, WebDriver... driver) {
        return WebDriverShooter.shoot(new PageShooter(), options, driver);
    }

    public static Screenshot frame(WebElement frame, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().build();
        return WebDriverShooter.frame(options, frame, driver);
    }

    public static Screenshot frame(WebElement frame, WebElement[] ignoredElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().ignoreElements(ignoredElements).build();
        return WebDriverShooter.frame(options, frame, driver);
    }

    public static Screenshot frame(ShooterOptions options, WebElement frame, WebDriver... driver) {
        return WebDriverShooter.shoot(new FrameShooter(frame), options, driver);
    }

    public static Screenshot element(WebElement element, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().build();
        return WebDriverShooter.element(options, element, driver);
    }

    public static Screenshot element(WebElement element, WebElement[] ignoredElements, WebDriver... driver) {
        ShooterOptions options = ShooterOptions.builder().ignoreElements(ignoredElements).build();
        return WebDriverShooter.element(options, element, driver);
    }

    public static Screenshot element(ShooterOptions options, WebElement element, WebDriver... driver) {
        return WebDriverShooter.shoot(new ElementShooter(element), options, driver);
    }

    //-------------------------------------------------------------------------------//

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

    protected static <Operator extends ShooterOperator> Screenshot shoot(WebDriverShooter<Operator> shooter, ShooterOptions options, WebDriver... args) {
        WebDriver driver = getDriver(args);
        Operator operator = shooter.operator(options, driver);
        return shooter.shoot(options, driver, operator);
    }

    //-------------------------------------------------------------------------------//

    protected BufferedImage screenshot(WebDriver driver) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new ShooterException(e);
        } finally {
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
    }

    //-------------------------------------------------------------------------------//

    protected abstract Operator operator(ShooterOptions options, WebDriver driver);
}
