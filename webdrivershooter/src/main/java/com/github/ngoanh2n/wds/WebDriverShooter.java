package com.github.ngoanh2n.wds;

import com.github.ngoanh2n.RuntimeError;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.ServiceLoader;

public abstract class WebDriverShooter<Options extends ShooterOptions, Operator extends ShooterOperator<Options>> extends ShooterStrategy<Options, Operator> {
    public static Screenshot page(WebDriver... driver) {
        PageOptions options = PageOptions.defaults();
        return WebDriverShooter.page(options, driver);
    }

    public static Screenshot page(WebElement[] elementsToIgnore, WebDriver... driver) {
        PageOptions options = PageOptions.builder().ignoreElements(elementsToIgnore).build();
        return WebDriverShooter.page(options, driver);
    }

    public static Screenshot page(PageOptions options, WebDriver... driver) {
        return WebDriverShooter.shoot(new PageShooter(), options, driver);
    }

    public static Screenshot frame(WebDriver... driver) {
        FrameOptions options = FrameOptions.defaults();
        return WebDriverShooter.frame(options, driver);
    }

    public static Screenshot frame(WebElement frame, WebDriver... driver) {
        FrameOptions options = FrameOptions.builder().setFrame(frame).build();
        return WebDriverShooter.frame(options, driver);
    }

    public static Screenshot frame(WebElement frame, WebElement[] elementsToIgnore, WebDriver... driver) {
        FrameOptions options = FrameOptions.builder().setFrame(frame).ignoreElements(elementsToIgnore).build();
        return WebDriverShooter.frame(options, driver);
    }

    public static Screenshot frame(FrameOptions options, WebDriver... driver) {
        return WebDriverShooter.shoot(new FrameShooter(), options, driver);
    }

    public static Screenshot element(WebDriver... driver) {
        return WebDriverShooter.element(ElementOptions.defaults(), driver);
    }

    public static Screenshot element(ElementOptions options, WebDriver... driver) {
        return WebDriverShooter.shoot(new ElementShooter(), options, driver);
    }

    //-------------------------------------------------------------------------------//

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

    protected static <Options extends ShooterOptions, Operator extends ShooterOperator<Options>> Screenshot shoot(WebDriverShooter<Options, Operator> shooter, Options options, WebDriver... args) {
        Operator operator;
        WebDriver driver = getDriver(args);

        switch (options.shooterStrategy()) {
            case 1:
                operator = shooter.byScroll0(options, driver);
                return shooter.shootScroll0(options, operator, driver);
            case 2:
                operator = shooter.byScrollY(options, driver);
                return shooter.shootScrollY(options, operator, driver);
            case 3:
                operator = shooter.byScrollX(options, driver);
                return shooter.shootScrollX(options, operator, driver);
            default:
                operator = shooter.byScrollXY(options, driver);
                return shooter.shootScrollXY(options, operator, driver);
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

    protected abstract Screenshot shootScroll0(Options options, Operator operator, WebDriver driver);

    protected abstract Screenshot shootScrollY(Options options, Operator operator, WebDriver driver);

    protected abstract Screenshot shootScrollX(Options options, Operator operator, WebDriver driver);

    protected abstract Screenshot shootScrollXY(Options options, Operator operator, WebDriver driver);
}
