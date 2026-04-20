package com.srm.bankbot.tests;

import com.srm.bankbot.base.BaseTest;
import com.srm.bankbot.model.Credentials;
import com.srm.bankbot.model.CustomerData;
import com.srm.bankbot.pages.EditCustomerPage;
import com.srm.bankbot.pages.ManagerHomePage;
import com.srm.bankbot.pages.NewCustomerPage;
import com.srm.bankbot.utils.CredentialService;
import com.srm.bankbot.utils.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CustomerManagementTests extends BaseTest {

    @Test
    public void verifyNewCustomerCreationGeneratesCustomerId() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new com.srm.bankbot.pages.LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        NewCustomerPage newCustomerPage = homePage.goToNewCustomerPage();
        String customerId = newCustomerPage.createCustomer(TestDataFactory.validCustomer());

        Assert.assertFalse(customerId.isBlank(), "Customer ID should be generated.");
        Assert.assertTrue(newCustomerPage.getSuccessHeading().contains("Customer Registered Successfully"),
                "Customer creation success message should be shown.");
    }

    @Test
    public void verifyCustomerAddressCanBeUpdated() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new com.srm.bankbot.pages.LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        String customerId = homePage.goToNewCustomerPage().createCustomer(TestDataFactory.validCustomer());

        EditCustomerPage editCustomerPage = homePage.goToEditCustomerPage().loadCustomer(customerId);
        String updatedAddress = "77 Updated Address Lane";
        String updateMessage = editCustomerPage.updateAddress(updatedAddress, "Coimbatore", "Tamil Nadu");

        Assert.assertTrue(updateMessage.contains("Customer details updated Successfully"),
                "Edit customer should confirm the update.");
    }

    @Test
    public void verifyDuplicateCustomerEmailShowsError() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new com.srm.bankbot.pages.LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        CustomerData originalCustomer = TestDataFactory.validCustomer();
        homePage.goToNewCustomerPage().createCustomer(originalCustomer);

        String duplicateEmailMessage = homePage.goToNewCustomerPage()
                .createCustomerExpectingFailure(TestDataFactory.customerWithEmail(originalCustomer.email()));

        Assert.assertFalse(duplicateEmailMessage.isBlank(), "Duplicate email should produce an error message.");
    }
}
