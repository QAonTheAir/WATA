package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    /**
     * Capture screenshot and save to screenshots folder with timestamp.
     *
     * @param driver WebDriver instance
     * @param testName Name of the test (used in filename)
     */
    public static void takeScreenshot(WebDriver driver, String testName) {
        try {
            // Generate file name with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "screenshots/" + testName + "_" + timestamp + ".png";

            // Create directory if not exists
            Files.createDirectories(Paths.get("screenshots"));

            // Take screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get(fileName));

            System.out.println("Screenshot saved: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
        }
    }


}
