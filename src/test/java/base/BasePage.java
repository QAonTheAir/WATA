package base;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utils.WaitUtils;

import java.util.ArrayList;

public class BasePage {
    protected WebDriver driver;
    // Define elements directly
    @FindBy(xpath = "//*[@class='ico-register']")
    public WebElement menuRegister;
    @FindBy(xpath = "//*[@class='ico-login']")
    public WebElement menuLogin;
    @FindBy(xpath = "//*[@class='ico-logout']")
    public WebElement menuLogout;
    @FindBy(xpath = "//*[@class='selected-price-range']")
    public WebElement priceSliderRange;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        // This line initializes all @FindBy elements
        PageFactory.initElements(driver, this);
    }



    public void sentText(WebElement element,String value) {
        element.sendKeys(value);
    }
    public void clickOnElement(WebElement element){
        element.click();
    }
    public void clickOnElementWithText(String btnText){
        String xpath = String.format("//*[text()='%s']", btnText);
        WaitUtils.waitForElementPresent(driver, By.xpath(xpath), 3);
        driver.findElement(By.xpath(xpath)).click();
    }
    public void clickOnButton(String btnText){
        String xpath = String.format("//button[text()='%s']", btnText);
        WaitUtils.waitForElementPresent(driver, By.xpath(xpath), 3);
        driver.findElement(By.xpath(xpath)).click();
    }

    public void openNewTab(WebDriver driver, String url) {
        // Open a new blank tab
        ((JavascriptExecutor) driver).executeScript("window.open()");

        // Switch to the new tab
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabs.size() - 1));

        // Navigate to the URL
        driver.get(url);
    }

    /**
     * Navigate to menu by path string like "Computers > Notebooks"
     */
    public void navigateMenu(String menuPath) {
        Actions actions = new Actions(driver);

        // Split the path (trim spaces around >)
        String[] parts = menuPath.split(">");
        String topMenu = parts[0].trim();
        String subMenu = parts.length > 1 ? parts[1].trim() : null;

        // Locate top menu item
        WebElement topMenuElement = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='" + topMenu + "']"));

        if (subMenu != null) {
            // Hover on top menu
            actions.moveToElement(topMenuElement).perform();

            // Locate submenu item
            WebElement subMenuElement = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='" + subMenu + "']"));

            // Click submenu
            subMenuElement.click();
        } else {
            // If only top menu provided, just click it
            topMenuElement.click();
        }
    }

    /**
     * Select dropdown option by visible text
     */
    public void selectByVisibleText(WebElement dropdownElement, String optionText) {
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(optionText);
    }

    /**
     * Set price range by exact min and max values
     * @param minPrice - desired minimum price
     * @param maxPrice - desired maximum price
     */
    public void setPriceRange(int minPrice, int maxPrice) {
        WebElement minHandle = driver.findElement(By.cssSelector("#price-range-slider .ui-slider-handle:nth-of-type(1)"));
        WebElement maxHandle = driver.findElement(By.cssSelector("#price-range-slider .ui-slider-handle:nth-of-type(2)"));

        WebElement fromEl = driver.findElement(By.cssSelector(".selected-price-range .from"));
        WebElement toEl   = driver.findElement(By.cssSelector(".selected-price-range .to"));

        Actions actions = new Actions(driver);

        // Move min handle until .from == minPrice
        while (Integer.parseInt(fromEl.getText().trim()) != minPrice) {
            int current = Integer.parseInt(fromEl.getText().trim());
            int step = current < minPrice ? 1 : -1; // adjust pixels per move
            actions.dragAndDropBy(minHandle, step, 0).perform();
        }

        // Move max handle until .to == maxPrice
        while (Integer.parseInt(toEl.getText().trim()) != maxPrice) {
            int current = Integer.parseInt(toEl.getText().trim());
            int step = current < maxPrice ? 1 : -1; // adjust pixels per move
            actions.dragAndDropBy(maxHandle, step, 0).perform();
        }

        System.out.println("Final price range: " + fromEl.getText() + " - " + toEl.getText());
    }

    public void setPriceRangeByJS(int minPrice, int maxPrice) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Update "from" value
        js.executeScript(
                "let fromElem = document.querySelector('.selected-price-range .from');" +
                        "fromElem.textContent = arguments[0];" +
                        "fromElem.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "fromElem.dispatchEvent(new Event('input', { bubbles: true }));",
                minPrice
        );

        // Update "to" value
        js.executeScript(
                "let toElem = document.querySelector('.selected-price-range .to');" +
                        "toElem.textContent = arguments[0];" +
                        "toElem.dispatchEvent(new Event('change', { bubbles: true }));" +
                        "toElem.dispatchEvent(new Event('input', { bubbles: true }));",
                maxPrice
        );

        // Back-check values
        String actualMin = (String) js.executeScript(
                "return document.querySelector('.selected-price-range .from').textContent.trim();"
        );
        String actualMax = (String) js.executeScript(
                "return document.querySelector('.selected-price-range .to').textContent.trim();"
        );

        if (!(String.valueOf(minPrice).equals(actualMin) && String.valueOf(maxPrice).equals(actualMax))) {
            throw new AssertionError("Price range not set correctly. Expected: ["
                    + minPrice + " - " + maxPrice + "], but got: ["
                    + actualMin + " - " + actualMax + "]");
        }
    }

    public void setPriceRangeBySlider(int minPrice, int maxPrice) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Update jQuery UI slider if available
        js.executeScript(
                "var slider = $('#price-range-slider');" +
                        "if (slider.length && slider.data('ui-slider')) {" +
                        "  slider.slider('values', [arguments[0], arguments[1]]);" +
                        "  slider.trigger('slidechange');" +  // force trigger event
                        "}",
                minPrice, maxPrice
        );

        // Back-check values
        String actualMin = (String) js.executeScript(
                "return document.querySelector('.selected-price-range .from').textContent.trim();"
        );
        String actualMax = (String) js.executeScript(
                "return document.querySelector('.selected-price-range .to').textContent.trim();"
        );

        if (!(String.valueOf(minPrice).equals(actualMin) && String.valueOf(maxPrice).equals(actualMax))) {
            throw new AssertionError("Price range not set correctly. Expected: ["
                    + minPrice + " - " + maxPrice + "], but got: ["
                    + actualMin + " - " + actualMax + "]");
        }
    }
}
