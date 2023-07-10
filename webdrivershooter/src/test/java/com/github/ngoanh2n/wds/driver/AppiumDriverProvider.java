package com.github.ngoanh2n.wds.driver;

import com.github.ngoanh2n.Property;
import com.github.ngoanh2n.YamlData;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author ngoanh2n
 */
@ParametersAreNonnullByDefault
public class AppiumDriverProvider {
    private static final Logger log = LoggerFactory.getLogger(AppiumDriverProvider.class);
    private static final Property<String> capabilitiesResource = Property.ofString("wds.appium.capabilities");

    public static WebDriver startDriver(@Nullable Capabilities otherCapabilities) {
        AppiumDriverLocalService service = startServer();
        Capabilities capabilities = readCapabilities().merge(otherCapabilities);
        return new AppiumDriver(service, capabilities);
    }

    public static AppiumDriverLocalService startServer() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub")
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error:warn")
                .withArgument(GeneralServerFlag.ALLOW_INSECURE, "chromedriver_autodownload")
                //.withArgument(() -> "--allow-insecure", "chromedriver_autodownload")
                ;
        return startServer(builder);
    }

    public static AppiumDriverLocalService startServer(AppiumServiceBuilder builder) {
        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        Runtime.getRuntime().addShutdownHook(new StopAppiumServerThread(service));

        if (!service.isRunning()) {
            service.start();
            log.debug("Appium server started");
        } else {
            log.debug("Appium server has started already");
        }
        //service.clearOutPutStreams();
        return service;
    }

    public static Capabilities readCapabilities() {
        String capabilitiesFile = capabilitiesResource.getValue();
        MutableCapabilities capabilities = new MutableCapabilities();
        YamlData.toMapFromResource(capabilitiesFile).forEach(capabilities::setCapability);

        System.getProperties().forEach((key, value) -> {
            if (String.valueOf(key).startsWith("appium:")) {
                capabilities.setCapability(String.valueOf(key), String.valueOf(value));
            }
        });
        return capabilities;
    }

    //-------------------------------------------------------------------------------//

    private final static class StopAppiumServerThread extends Thread {
        private final AppiumDriverLocalService service;

        private StopAppiumServerThread(AppiumDriverLocalService service) {
            this.service = service;
        }

        @Override
        public void run() {
            if (service.isRunning()) {
                service.stop();
                log.debug("Appium server stopped");
            }
        }
    }
}
