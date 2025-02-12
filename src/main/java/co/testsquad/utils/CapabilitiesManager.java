package co.testsquad.utils;

import co.testsquad.config.FrameworkConfig;
import org.openqa.selenium.remote.DesiredCapabilities;

public class CapabilitiesManager {
    
    public static DesiredCapabilities getCapabilities(String platform) {
        FrameworkConfig config = FrameworkConfig.getInstance();
        return config.getProperty("test.execution.platform").equals("lambdatest") 
            ? getLambdaTestCapabilities(platform) 
            : getLocalCapabilities(platform);
    }

    private static DesiredCapabilities getLocalCapabilities(String platform) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        FrameworkConfig config = FrameworkConfig.getInstance();
        
        switch (platform.toLowerCase()) {
            case "android":
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("automationName", "UiAutomator2");
                capabilities.setCapability("deviceName", config.getProperty("android.deviceName"));
                capabilities.setCapability("app", config.getProperty("android.app.path"));
                capabilities.setCapability("appPackage", config.getProperty("android.app.package"));
                capabilities.setCapability("appActivity", config.getProperty("android.app.activity"));
                break;
                
            case "ios":
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("automationName", "XCUITest");
                capabilities.setCapability("deviceName", config.getProperty("ios.deviceName"));
                capabilities.setCapability("platformVersion", config.getProperty("ios.version"));
                capabilities.setCapability("app", config.getProperty("ios.app.path"));
                capabilities.setCapability("bundleId", config.getProperty("ios.bundleId"));
                break;
                
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
        
        return capabilities;
    }

    private static DesiredCapabilities getLambdaTestCapabilities(String platform) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        FrameworkConfig config = FrameworkConfig.getInstance();

        // Common LambdaTest capabilities
        capabilities.setCapability("build", config.getProperty("lambdatest.build"));
        capabilities.setCapability("name", "Mobile Test Execution");
        capabilities.setCapability("project", config.getProperty("lambdatest.project"));
        capabilities.setCapability("isRealMobile", Boolean.parseBoolean(config.getProperty("lambdatest.isRealMobile")));
        capabilities.setCapability("video", Boolean.parseBoolean(config.getProperty("lambdatest.video")));
        capabilities.setCapability("visual", Boolean.parseBoolean(config.getProperty("lambdatest.visual")));
        capabilities.setCapability("console", Boolean.parseBoolean(config.getProperty("lambdatest.console")));
        capabilities.setCapability("devicelog", Boolean.parseBoolean(config.getProperty("lambdatest.devicelog")));

        switch (platform.toLowerCase()) {
            case "android":
                capabilities.setCapability("platformName", "Android");
                capabilities.setCapability("deviceName", config.getProperty("android.deviceName"));
                capabilities.setCapability("platformVersion", "12.0");
                capabilities.setCapability("app", uploadAppToLambdaTest(config.getProperty("android.app.path")));
                break;
                
            case "ios":
                capabilities.setCapability("platformName", "iOS");
                capabilities.setCapability("deviceName", config.getProperty("ios.deviceName"));
                capabilities.setCapability("platformVersion", config.getProperty("ios.version"));
                capabilities.setCapability("app", uploadAppToLambdaTest(config.getProperty("ios.app.path")));
                break;
                
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
        
        return capabilities;
    }

    private static String uploadAppToLambdaTest(String appPath) {
        // TODO: Implement app upload to LambdaTest and return app URL
        // You can use LambdaTest REST API to upload the app
        return appPath;
    }
} 