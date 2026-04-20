package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditAccountPage extends BasePage {

    private final By accountIdField = By.name("accountno");
    private final By submitAccountIdButton = By.name("AccSubmit");
    private final By accountTypeDropdown = By.name("a_type");
    private final By submitUpdateButton = By.name("AccSubmit");
    private final By successMessage = By.cssSelector("p.heading3");

    public EditAccountPage(WebDriver driver) {
        super(driver);
    }

    public EditAccountPage loadAccount(String accountId) {
        type(accountIdField, accountId);
        click(submitAccountIdButton);
        waitForPageReady();
        skipIfPageUnavailable("Edit Account page");
        return this;
    }

    public String updateAccountType(String accountType) {
        selectByVisibleText(accountTypeDropdown, accountType);
        click(submitUpdateButton);
        waitForPageReady();
        skipIfPageUnavailable("Edit Account update page");
        return waitForAnyMessage(successMessage, By.tagName("body"));
    }

    public String getSelectedAccountType() {
        return waitForVisibility(accountTypeDropdown).getDomProperty("value");
    }
}
