package com.srm.bankbot.tests;

import com.srm.bankbot.base.BaseTest;
import com.srm.bankbot.model.AccountData;
import com.srm.bankbot.model.Credentials;
import com.srm.bankbot.pages.EditAccountPage;
import com.srm.bankbot.pages.LoginPage;
import com.srm.bankbot.pages.ManagerHomePage;
import com.srm.bankbot.utils.CredentialService;
import com.srm.bankbot.utils.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountManagementTests extends BaseTest {

    @Test
    public void verifyNewAccountCreationGeneratesAccountNumber() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        String customerId = homePage.goToNewCustomerPage().createCustomer(TestDataFactory.validCustomer());

        String accountId = homePage.goToNewAccountPage().createAccount(TestDataFactory.validAccount(customerId));
        Assert.assertFalse(accountId.isBlank(), "Account ID should be generated for the new account.");
    }

    @Test
    public void verifyAccountTypeCanBeEdited() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        String customerId = homePage.goToNewCustomerPage().createCustomer(TestDataFactory.validCustomer());
        String accountId = homePage.goToNewAccountPage().createAccount(TestDataFactory.validAccount(customerId));

        EditAccountPage editAccountPage = homePage.goToEditAccountPage().loadAccount(accountId);
        String message = editAccountPage.updateAccountType("Current");

        Assert.assertTrue(message.contains("Account details updated Successfully") || message.contains("Account Generated Successfully"),
                "Account update confirmation should be displayed.");
    }

    @Test
    public void verifyInvalidCustomerIdShowsAccountCreationError() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        String message = homePage.goToNewAccountPage().createAccountExpectingFailure(new AccountData("99999999", "Savings", "5000"));

        Assert.assertFalse(message.isBlank(), "Invalid customer id should produce an error.");
    }
}
