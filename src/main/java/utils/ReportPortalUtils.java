package utils;

import com.epam.reportportal.message.ReportPortalMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReportPortalUtils {
    private static final Logger LOGGER = LogManager.getLogger(ResolverUtil.Test.class);
    public static void attachScreenshot(WebDriver driver, String message) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            // Save screenshot to a temporary file
            saveScreenshotToFile(driver, "temp_screenshot.png");
            ReportPortalMessage RPmessage = new ReportPortalMessage(new File("temp_screenshot.png"), message);
            LOGGER.info(RPmessage);



        } catch (Exception e) {
            System.out.println("Failed to attach screenshot to ReportPortal: " + e.getMessage());
        }
    }

    public static void saveScreenshotToFile(WebDriver driver, String filePath) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(screenshot);
                System.out.println("Screenshot saved at: " + filePath);
            }
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }
}
