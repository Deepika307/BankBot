package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import com.srm.bankbot.model.AccountData;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewAccountPage extends BasePage {

    private final By customerIdField = By.name("cusid");
    private final By accountTypeDropdown = By.name("selaccount");
    private final By initialDepositField = By.name("inideposit");
    private final By submitButton = By.name("button2");
    private final By accountRows = By.cssSelector("table table tr");

    public NewAccountPage(WebDriver driver) {
        super(driver);
    }

    public String createAccount(AccountData accountData) {
        type(customerIdField, accountData.customerId());
        selectByVisibleText(accountTypeDropdown, accountData.accountType());
        type(initialDepositField, accountData.initialDeposit());
        click(submitButton);
        waitForPageReady();
        return readTableValue("Account ID");
    }

    public String createAccountExpectingFailure(AccountData accountData) {
        type(customerIdField, accountData.customerId());
        selectByVisibleText(accountTypeDropdown, accountData.accountType());
        type(initialDepositField, accountData.initialDeposit());
        click(submitButton);
        String alertText = readAlertTextAndAccept();
        if (!alertText.isBlank()) {
            return alertText;
        }
        return waitForAnyMessage(By.cssSelector("table table td"), By.tagName("body"));
    }

    private String readTableValue(String label) {
        List<WebElement> rows = driver.findElements(accountRows);
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if (columns.size() >= 2 && columns.get(0).getText().trim().equalsIgnoreCase(label)) {
                return columns.get(1).getText().trim();
            }
        }
        throw new IllegalStateException("Unable to read row with label: " + label);
    }
}
