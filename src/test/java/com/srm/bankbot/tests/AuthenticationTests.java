package com.srm.bankbot.tests;

import com.srm.bankbot.base.BaseTest;
import com.srm.bankbot.config.ConfigReader;
import com.srm.bankbot.model.Credentials;
import com.srm.bankbot.pages.LoginPage;
import com.srm.bankbot.pages.ManagerHomePage;
import com.srm.bankbot.utils.CredentialService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AuthenticationTests extends BaseTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        ConfigReader config = ConfigReader.getInstance();
        return new Object[][]{
                {"validManagerLogin", true, "", ""},
                {"invalidManagerLogin", false, config.getInvalidUserId(), config.getInvalidPassword()}
        };
    }

    @Test(dataProvider = "loginData")
    public void verifyManagerLoginScenarios(String scenario, boolean shouldSucceed, String userId, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoaded(), "Login page should load.");

        if (shouldSucceed) {
            Credentials credentials = CredentialService.resolveManagerCredentials();
            ManagerHomePage homePage = loginPage.login(credentials.userId(), credentials.password());
            Assert.assertTrue(homePage.isLoaded(), "Manager home page should be visible after successful login.");
            Assert.assertTrue(homePage.getManagerBannerText().contains("Manger Id"), "Manager banner should contain the manager id.");
        } else {
            String errorMessage = loginPage.loginExpectingFailure(userId, password);
            Assert.assertFalse(errorMessage.isBlank(), "Invalid login should show an error.");
        }
    }

    @Test
    public void verifyLogoutRedirectsToLoginPage() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        LoginPage loginPage = homePage.logout();
        Assert.assertTrue(loginPage.isLoaded(), "Logout should return the user to the login page.");
    }

    @Test
    public void verifyBlankLoginShowsValidationMessage() {
        String validationMessage = new LoginPage(getDriver()).clickLoginWithBlankFields();
        Assert.assertFalse(validationMessage.isBlank(), "Blank login should trigger a validation message.");
    }
}
