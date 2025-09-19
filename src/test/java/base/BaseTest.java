package base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ReportPortalUtils;
import utils.ScreenshotUtils;

public class BaseTest {
    protected WebDriver driver;

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            ScreenshotUtils.takeScreenshot(driver, result.getName());
            // attach screenshot to ReportPortal
            ReportPortalUtils.attachScreenshot(driver, "Screenshot on failure for test: " + result.getName());

        }
    }
    @AfterSuite
    public void cleanUp() {
        DriverFactory.quitDriver();
    }
    @BeforeClass
    @org.testng.annotations.Parameters("env")
    public void setUp(@org.testng.annotations.Optional("demo") String env) {
        // Load config for the given environment
        ConfigReader.loadConfig(env);
        String browser = ConfigReader.getProperty("browser");
        driver = DriverFactory.initDriver(browser);
        driver.get(ConfigReader.getProperty("base.url"));
    }

    public WebDriver getDriver() {
        return driver;
    }
}
