package co.testsquad.core;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

public class AppiumServerManager {
    private static AppiumDriverLocalService server;
    private static final int DEFAULT_PORT = 4723;

    public static void startServer() {
        if (!isServerRunning()) {
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .usingPort(DEFAULT_PORT)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "error");

            // Set Node.js and Appium paths based on OS
            if (SystemUtils.IS_OS_WINDOWS) {
                builder.withAppiumJS(new File(System.getProperty("user.home") + 
                    "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                    .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"));
            } else {
                builder.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js"))
                    .usingDriverExecutable(new File("/usr/local/bin/node"));
            }

            server = AppiumDriverLocalService.buildService(builder);
            server.start();

            if (!server.isRunning()) {
                throw new RuntimeException("Failed to start Appium server");
            }
        }
    }

    public static void stopServer() {
        if (server != null && server.isRunning()) {
            server.stop();
        }
    }

    public static URL getServerUrl() {
        return server != null ? server.getUrl() : null;
    }

    private static boolean isServerRunning() {
        try (ServerSocket serverSocket = new ServerSocket(DEFAULT_PORT)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }
} 