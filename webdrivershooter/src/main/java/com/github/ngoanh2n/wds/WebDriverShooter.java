package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.RuntimeError;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

public abstract class WebDriverShooter {
    protected static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeError(e);
        }
    }

    protected static WebDriver driver(WebDriver... args) {
        if (args.length != 0) {
            if (args[0] != null) {
                return args[0];
            }
            throw new RuntimeError("You have passed a nullable WebDriver");
        } else {
            ServiceLoader<WebDriverProvider> serviceLoader = ServiceLoader.load(WebDriverProvider.class);
            Iterator<WebDriverProvider> serviceLoaders = serviceLoader.iterator();

            if (serviceLoaders.hasNext()) {
                return serviceLoaders.next().provide();
            }
            throw new RuntimeError("You have not implemented WebDriverProvider");
        }
    }

    protected static Screenshot shoot(WebDriverShooter shooter, ShooterOptions options, WebDriver... args) {
        WebDriver driver = driver(args);
        Browser browser = new Browser(driver, options);

        switch (options.shooterStrategy()) {
            case 1:
                return new Screenshot(shooter.shootViewport(options, driver, browser));
            case 2:
                return new Screenshot(shooter.shootScrollVertical(options, driver, browser));
            case 3:
                return new Screenshot(shooter.shootScrollHorizontal(options, driver, browser));
            default:
                return new Screenshot(shooter.shootScrollBothDirection(options, driver, browser));
        }
    }

    //-------------------------------------------------------------------------------//

    protected BufferedImage shootScreen(WebDriver driver) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeError(e);
        } finally {
            if (file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.delete();
            }
        }
    }

    protected abstract BufferedImage shootViewport(ShooterOptions options, WebDriver driver, Browser browser);

    protected abstract BufferedImage shootScrollVertical(ShooterOptions options, WebDriver driver, Browser browser);

    protected abstract BufferedImage shootScrollHorizontal(ShooterOptions options, WebDriver driver, Browser browser);

    protected abstract BufferedImage shootScrollBothDirection(ShooterOptions options, WebDriver driver, Browser browser);
}
