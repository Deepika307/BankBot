package com.srm.bankbot.tests;

import com.srm.bankbot.base.BaseTest;
import com.srm.bankbot.model.AccountData;
import com.srm.bankbot.model.Credentials;
import com.srm.bankbot.model.FundTransferData;
import com.srm.bankbot.pages.BalanceEnquiryPage;
import com.srm.bankbot.pages.LoginPage;
import com.srm.bankbot.pages.ManagerHomePage;
import com.srm.bankbot.utils.CredentialService;
import com.srm.bankbot.utils.TestDataFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FundTransferTests extends BaseTest {

    @Test
    public void verifyFundTransferSuccessAndBalanceUpdate() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        String customerId = homePage.goToNewCustomerPage().createCustomer(TestDataFactory.validCustomer());
        String payerAccount = homePage.goToNewAccountPage().createAccount(new AccountData(customerId, "Savings", "5000"));
        String payeeAccount = homePage.goToNewAccountPage().createAccount(new AccountData(customerId, "Current", "1000"));

        BalanceEnquiryPage balanceEnquiryPage = homePage.goToBalanceEnquiryPage();
        int payerBalanceBefore = Integer.parseInt(balanceEnquiryPage.fetchBalance(payerAccount));
        int payeeBalanceBefore = Integer.parseInt(balanceEnquiryPage.fetchBalance(payeeAccount));

        String transferResult = homePage.goToFundTransferPage().transferFunds(TestDataFactory.validTransfer(payerAccount, payeeAccount));
        int payerBalanceAfter = Integer.parseInt(homePage.goToBalanceEnquiryPage().fetchBalance(payerAccount));
        int payeeBalanceAfter = Integer.parseInt(homePage.goToBalanceEnquiryPage().fetchBalance(payeeAccount));

        Assert.assertFalse(transferResult.isBlank(), "Fund transfer should return a success message.");
        Assert.assertTrue(payerBalanceAfter < payerBalanceBefore, "Payer balance should reduce after transfer.");
        Assert.assertTrue(payeeBalanceAfter > payeeBalanceBefore, "Payee balance should increase after transfer.");
    }

    @Test
    public void verifyInvalidPayeeAccountShowsError() {
        Credentials credentials = CredentialService.resolveManagerCredentials();
        ManagerHomePage homePage = new LoginPage(getDriver()).login(credentials.userId(), credentials.password());
        String customerId = homePage.goToNewCustomerPage().createCustomer(TestDataFactory.validCustomer());
        String payerAccount = homePage.goToNewAccountPage().createAccount(TestDataFactory.validAccount(customerId));

        FundTransferData invalidTransfer = new FundTransferData(payerAccount, "99999999", "100", "Invalid payee");
        String errorMessage = homePage.goToFundTransferPage().transferFundsExpectingFailure(invalidTransfer);

        Assert.assertFalse(errorMessage.isBlank(), "Invalid payee account should produce an error.");
    }
}
