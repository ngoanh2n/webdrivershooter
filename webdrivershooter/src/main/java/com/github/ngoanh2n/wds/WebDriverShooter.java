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

public abstract class WebDriverShooter<X extends ShooterOptions, Y extends ShooterOperator<X>> extends ShooterStrategy<X, Y> {
    protected static WebDriver getDriver(WebDriver... args) {
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

    protected static <X extends ShooterOptions, Y extends ShooterOperator<X>> Screenshot shoot(WebDriverShooter<X, Y> shooter, X options, WebDriver... args) {
        switch (options.shooterStrategy()) {
            case 1:
                return shooter.shootViewport(options, getDriver(args));
            case 2:
                return shooter.shootVerticalScroll(options, getDriver(args));
            case 3:
                return shooter.shootHorizontalScroll(options, getDriver(args));
            default:
                return shooter.shootBothDirectionScroll(options, getDriver(args));
        }
    }

    //-------------------------------------------------------------------------------//

    protected BufferedImage screenshot(WebDriver driver) {
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

    protected abstract Screenshot shootViewport(X options, WebDriver driver);

    protected abstract Screenshot shootVerticalScroll(X options, WebDriver driver);

    protected abstract Screenshot shootHorizontalScroll(X options, WebDriver driver);

    protected abstract Screenshot shootBothDirectionScroll(X options, WebDriver driver);
}
