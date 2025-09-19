package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchPage extends BasePage {
    public SearchPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(id = "small-searchterms")
    public WebElement txtSearch;
    @FindBy(id = "products-orderby")
    public WebElement dropdownProductsOrderBy;


    String productTitleCssSelector = ".product-grid .item-box h2.product-title a";

    /**
     * Get all product titles from product grid
     */
    public List<String> getAllProductTitles(WebDriver driver) {
        List<WebElement> productElements = driver.findElements(By.cssSelector(".product-grid .item-box h2.product-title a"));
        List<String> titles = new ArrayList<>();
        try {
            if (productElements.isEmpty()) {
                System.out.println("No products found on the search results page.");
                return titles; // Return empty list if no products found
            }
        } catch (Exception e) {
            System.out.println("Error while locating product elements: " + e.getMessage());
            return titles; // Return empty list in case of error
        }
        try {
            for (WebElement product : productElements) {
                titles.add(product.getText());
                System.out.println("Retrying title: " + product.getText());
            }
        }catch (StaleElementReferenceException e) {
            System.out.println("StaleElementReferenceException Error while extracting product titles: " + e.getMessage());
            productElements = driver.findElements(By.cssSelector(".product-grid .item-box h2.product-title a"));
            for (WebElement product : productElements) {
                titles.add(product.getText());
                System.out.println("Retrying title: " + product.getText());
            }
        }


        return titles;
    }

    /**
     * Verify that all product titles contain the expected keyword
     */
    public boolean verifyKeywordInAllTitles(List<String> titles ,String keyword) {

        for (String title : titles) {
            String titleLowerCase = title.toLowerCase();
            if (!titleLowerCase.contains(keyword.toLowerCase())) {
                System.out.println("Keyword not found in title: " + title);
                return false;
            }
        }
        return true;
    }
    /**
     * Get all product prices from the current page.
     *
     * @param driver WebDriver instance
     * @return List of product prices as Strings
     */
    public List<String> getAllProductPrices(WebDriver driver) {
        List<String> prices = new ArrayList<>();

        List<WebElement> priceElements = driver.findElements(By.cssSelector(".price.actual-price"));
        for (WebElement priceElement : priceElements) {
            prices.add(priceElement.getText());
        }

        return prices;
    }
    /**
     * Convert list of price strings (like "$1,800.00") to numeric values (Double).
     *
     * @param priceStrings List of product prices as strings
     * @return List of product prices as numbers
     */
    public List<Double> convertPricesToNumbers(List<String> priceStrings) {
        List<Double> numericPrices = new ArrayList<>();

        for (String price : priceStrings) {
            // Remove $ sign, commas, and extra spaces
            String cleanPrice = price.replaceAll("[$,]", "").trim();
            numericPrices.add(Double.parseDouble(cleanPrice));
        }

        return numericPrices;
    }

    /**
     * Selects a filter option under a given filter group
     * Example: selectFilter("CPU Type", "Intel Core i7")
     */
    public void selectFilter(WebDriver driver,String groupName, String optionLabel) {
        // Locate the group by its <strong> title
        String groupXpath = "//div[@class='filter-content']/ul[.//strong[text()='" + groupName + "']]";
        WebElement groupElement = driver.findElement(By.xpath(groupXpath));

        // Find all filter options inside this group
        List<WebElement> options = groupElement.findElements(By.xpath(".//li[@class='item']/label"));

        for (WebElement option : options) {
            if (option.getText().trim().equalsIgnoreCase(optionLabel.trim())) {
                option.click(); // click on label → triggers checkbox
                return;
            }
        }

        throw new RuntimeException("Filter option '" + optionLabel + "' not found under group '" + groupName + "'");
    }

    public  List<String> getAllProductUrls(WebDriver driver) {
        List<String> urls = new ArrayList<>();
        List<WebElement> productLinks = driver.findElements(
                By.cssSelector(".products-container .product-item .details h2.product-title a")
        );

        for (WebElement link : productLinks) {
            urls.add(link.getAttribute("href"));
        }

        return urls;
    }

    public Map<String, String> getProductSpecifications(WebDriver driver) {
        Map<String, String> specs = new HashMap<>();

        // Locate all spec rows
        List<WebElement> rows = driver.findElements(By.cssSelector("tr"));

        for (WebElement row : rows) {
            try {
                WebElement nameCell = row.findElement(By.cssSelector(".spec-name"));
                WebElement valueCell = row.findElement(By.cssSelector(".spec-value"));

                String name = nameCell.getText().trim();
                String value = valueCell.getText().trim();

                specs.put(name, value);
            } catch (Exception e) {
                // Skip if row doesn't contain spec-name/spec-value
            }
        }

        return specs;
    }
    public Map<String, String> getProductManufacturer(WebDriver driver) {
        Map<String, String> specs = new HashMap<>();

        // Locate all spec rows
        List<WebElement> rows = driver.findElements(By.cssSelector("div.manufacturers"));

        for (WebElement row : rows) {
            try {
                WebElement nameCell = row.findElement(By.cssSelector(".label"));
                WebElement valueCell = row.findElement(By.cssSelector(".value"));

                String name = nameCell.getText().trim();
                String value = valueCell.getText().trim();

                specs.put(name, value);
            } catch (Exception e) {
                // Skip if row doesn't contain spec-name/spec-value
            }
        }

        return specs;
    }

    /**
     * Select a manufacturer by visible label text.
     *
     * @param manufacturerName The name of the manufacturer (e.g., "Apple", "HP").
     */
    public void selectManufacturer(WebDriver driver,String manufacturerName) {
        // Locate manufacturer filter block
        WebElement filterBlock = driver.findElement(
                By.cssSelector(".product-filter.product-manufacturer-filter .filter-content")
        );

        // Find all label elements inside manufacturer filter
        for (WebElement label : filterBlock.findElements(By.tagName("label"))) {
            if (label.getText().trim().equalsIgnoreCase(manufacturerName)) {
                label.click();  // Click label (will toggle the checkbox)
                return;
            }
        }

        throw new RuntimeException("Manufacturer filter '" + manufacturerName + "' not found!");
    }

}
