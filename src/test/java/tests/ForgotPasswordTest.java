package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import utils.AssertUtils;
import utils.ConfigReader;
import utils.WaitUtils;

import static utils.DataGenerator.*;

public class ForgotPasswordTest extends BaseTest {

    @Test
    public void forgotPassword() {
        //Precondition: User must be registered
        HomePage home = new HomePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        PasswordRecoveryPage passwordRecoveryPage = new PasswordRecoveryPage(driver);
        MailsacPage mailsacPage = new MailsacPage(driver);

        home.clickOnElement(home.menuRegister);
        String email = getRandomEmail();
        String username = getUsernameFromEmail(email);
        registerPage.registerUser("A","B",email,"ABC Corp","123456","123456");
        Assert.assertTrue(home.menuLogout.isDisplayed(), "Your registration Failed");
        home.clickOnElement(home.menuLogout);
        WaitUtils.waitForPageLoad(driver,30);

        // Load values from config file
        String emailUrl = ConfigReader.getProperty("email.url");

        home.clickOnElement(home.menuLogin);
        loginPage.clickOnElement(loginPage.lnkForgotPassword);
        passwordRecoveryPage.sentText(passwordRecoveryPage.txtEmail, email);
        passwordRecoveryPage.clickOnButton("Recover");
        AssertUtils.assertTextPresent(driver,"Email with instructions has been sent to you.");

        passwordRecoveryPage.openNewTab(driver, emailUrl);
        mailsacPage.sentText(mailsacPage.txtEmail, username);
        mailsacPage.clickOnButton("Check the mail!");
        Assert.assertEquals(mailsacPage.getFirstMessageSubject(driver),"Subject of the forgot password email","Forgot password email not received");
        mailsacPage.openFirstMessage(driver);
        //TBD


    }
}
