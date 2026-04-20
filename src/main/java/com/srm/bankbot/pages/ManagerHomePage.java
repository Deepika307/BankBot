package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ManagerHomePage extends BasePage {

    private final By managerIdBanner = By.xpath("//*[contains(text(),'Manger Id') or contains(text(),'Manager Id')]");
    private final By logoutLink = By.linkText("Log out");
    private final By newCustomerLink = By.linkText("New Customer");
    private final By editCustomerLink = By.linkText("Edit Customer");
    private final By newAccountLink = By.linkText("New Account");
    private final By editAccountLink = By.linkText("Edit Account");
    private final By fundTransferLink = By.linkText("Fund Transfer");
    private final By balanceEnquiryLink = By.linkText("Balance Enquiry");

    public ManagerHomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForPageReady();
        return isDisplayed(managerIdBanner);
    }

    public String getManagerBannerText() {
        return getText(managerIdBanner);
    }

    public LoginPage logout() {
        click(logoutLink);
        readAlertTextAndAccept();
        waitForUrlContains("index.php");
        return new LoginPage(driver);
    }

    public NewCustomerPage goToNewCustomerPage() {
        click(newCustomerLink);
        return new NewCustomerPage(driver);
    }

    public EditCustomerPage goToEditCustomerPage() {
        click(editCustomerLink);
        return new EditCustomerPage(driver);
    }

    public NewAccountPage goToNewAccountPage() {
        click(newAccountLink);
        return new NewAccountPage(driver);
    }

    public EditAccountPage goToEditAccountPage() {
        click(editAccountLink);
        return new EditAccountPage(driver);
    }

    public FundTransferPage goToFundTransferPage() {
        click(fundTransferLink);
        return new FundTransferPage(driver);
    }

    public BalanceEnquiryPage goToBalanceEnquiryPage() {
        click(balanceEnquiryLink);
        return new BalanceEnquiryPage(driver);
    }
}
