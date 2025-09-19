package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PasswordRecoveryPage extends BasePage {

    @FindBy(id = "Email")
    public WebElement txtEmail;


    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
    }



}
