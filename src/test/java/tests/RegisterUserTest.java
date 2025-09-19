package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.RegisterPage;


import static utils.DataGenerator.getRandomEmail;

public class RegisterUserTest extends BaseTest {

    @Test
    public void registerNewUser() {

        HomePage home = new HomePage(driver);
        RegisterPage registerPage = new RegisterPage(driver);
        home.clickOnElement(home.menuRegister);
        registerPage.registerUser("A","B",getRandomEmail(),"ABC Corp","123456","123456");

        Assert.assertTrue(home.menuLogout.isDisplayed(), "Your registration Failed");
    }
}
