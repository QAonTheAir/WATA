package utils;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class AssertUtils extends BaseTest {


    /**
     * Verify text is present in the entire page source
     */
    public static void assertTextPresent(WebDriver driver, String expectedText) {
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(expectedText),
                "Expected text not found in page: " + expectedText);
    }

    // Assert list is sorted (increase or decrease)
    public static void assertPricesOrder(List<Double> prices, String orderType) {
        for (int i = 0; i < prices.size() - 1; i++) {
            double current = prices.get(i);
            double next = prices.get(i + 1);

            if (orderType.equalsIgnoreCase("increase") || orderType.equalsIgnoreCase("asc")) {
                Assert.assertTrue(current <= next,
                        "List is not in ascending order at index " + i +
                                ": " + current + " > " + next);
            } else if (orderType.equalsIgnoreCase("decrease") || orderType.equalsIgnoreCase("desc")) {
                Assert.assertTrue(current >= next,
                        "List is not in descending order at index " + i +
                                ": " + current + " < " + next);
            } else {
                throw new IllegalArgumentException("Invalid orderType: " + orderType +
                        " (use 'increase/asc' or 'decrease/desc')");
            }
        }
    }
}
