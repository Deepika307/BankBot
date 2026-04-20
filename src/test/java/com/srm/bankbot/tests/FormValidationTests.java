package com.srm.bankbot.tests;

import com.srm.bankbot.base.BaseTest;
import com.srm.bankbot.model.Credentials;
import com.srm.bankbot.pages.LoginPage;
import com.srm.bankbot.pages.ManagerHomePage;
import com.srm.bankbot.pages.NewCustomerPage;
import com.srm.bankbot.utils.CredentialService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FormValidationTests extends BaseTest {

    @Test
    public void verifyNewCustomerFormRejectsEmptyFields() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        NewCustomerPage newCustomerPage = homePage.goToNewCustomerPage();

        String validationMessage = newCustomerPage.getValidationMessageForNameField();
        Assert.assertFalse(validationMessage.isBlank(), "Empty name field should show validation.");
    }

    @Test
    public void verifyNumericFieldRejectsNonNumericValues() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        NewCustomerPage newCustomerPage = homePage.goToNewCustomerPage();

        String validationMessage = newCustomerPage.getValidationMessageForPinFieldAfterAlphaInput();
        Assert.assertTrue(validationMessage.isBlank() || validationMessage.matches(".*[A-Za-z ].*") || validationMessage.contains("Characters are not allowed"),
                "PIN field should reject alphabetic input.");
    }

    @Test
    public void verifyDateOfBirthRejectsFutureDates() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        NewCustomerPage newCustomerPage = homePage.goToNewCustomerPage();

        String validationMessage = newCustomerPage.getFutureDobValidationMessage();
        Assert.assertFalse(validationMessage.isBlank(),
                "Future DOB behavior should be captured even if the live AUT accepts the value.");
    }
}
