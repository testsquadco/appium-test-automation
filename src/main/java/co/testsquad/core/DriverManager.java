package co.testsquad.core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import co.testsquad.config.FrameworkConfig;

import java.net.URL;

public class DriverManager {
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    private DriverManager() {}

    public static AppiumDriver getDriver() {
        if (driver.get() == null) {
            throw new IllegalStateException("Driver has not been initialized");
        }
        return driver.get();
    }

    public static void initializeDriver(String platform, DesiredCapabilities capabilities) {
        try {
            FrameworkConfig config = FrameworkConfig.getInstance();
            String executionPlatform = config.getProperty("test.execution.platform");
            URL serverUrl;
            
            if ("lambdatest".equals(executionPlatform)) {
                String username = System.getenv("LT_USERNAME");
                String accessKey = System.getenv("LT_ACCESS_KEY");
                serverUrl = new URL(config.getProperty("lambdatest.hub"));
            } else {
                AppiumServerManager.startServer();
                serverUrl = AppiumServerManager.getServerUrl();
            }
            
            switch (platform.toLowerCase()) {
                case "android":
                    driver.set(new AndroidDriver(serverUrl, capabilities));
                    break;
                case "ios":
                    driver.set(new IOSDriver(serverUrl, capabilities));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported platform: " + platform);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize driver", e);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            if ("local".equals(FrameworkConfig.getInstance().getProperty("test.execution.platform"))) {
                AppiumServerManager.stopServer();
            }
        }
    }
} 