package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.WaitUtils;

public class RegisterPage extends BasePage {
    // Define elements directly
    @FindBy(id = "gender-male")
    private WebElement rdoGenderMale;
    @FindBy(id = "FirstName")
    private WebElement txtFirstName;
    @FindBy(id = "LastName")
    private WebElement txtLastName;
    @FindBy(id = "Email")
    private WebElement txtEmail;
    @FindBy(id = "Company")
    private WebElement txtCompanyName;
    @FindBy(id = "Password")
    private WebElement txtPassword;
    @FindBy(id = "ConfirmPassword")
    private WebElement txtConfirmPassword;
    @FindBy(id = "register-button")
    private WebElement btnRegister;
    @FindBy (xpath = "//*[text()='Your registration completed']")
    public WebElement txtRegisterCompleted;

    public RegisterPage(WebDriver driver) {
        super(driver);
    }
    public RegisterPage selectGenderMale() {
        rdoGenderMale.click();
        return this;
    }

    public void registerUser(String firstName, String lastName, String email, String companyName, String password, String confirmPassword){
        selectGenderMale();
        sentText(txtFirstName,firstName);
        sentText(txtLastName,lastName);
        sentText(txtEmail,email);
        sentText(txtCompanyName,companyName);
        sentText(txtPassword,password);
        sentText(txtConfirmPassword,confirmPassword);
        clickOnElement(btnRegister);
        clickOnElementWithText("Continue");
        WaitUtils.waitForPageLoad(driver,30);
    }


}
