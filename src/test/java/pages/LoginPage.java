package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//a[@href='/passwordrecovery']")
    public WebElement lnkForgotPassword;
    public LoginPage(WebDriver driver) {
        super(driver);
    }
}
