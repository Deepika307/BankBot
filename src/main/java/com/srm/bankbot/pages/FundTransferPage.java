package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import com.srm.bankbot.model.FundTransferData;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FundTransferPage extends BasePage {

    private final By payerAccountField = By.name("payersaccount");
    private final By payeeAccountField = By.name("payeeaccount");
    private final By amountField = By.name("ammount");
    private final By descriptionField = By.name("desc");
    private final By submitButton = By.name("AccSubmit");
    private final By resultRows = By.cssSelector("table table tr");

    public FundTransferPage(WebDriver driver) {
        super(driver);
    }

    public String transferFunds(FundTransferData transferData) {
        fillTransferForm(transferData);
        click(submitButton);
        waitForPageReady();
        return readTableValue("Fund Transfer Details");
    }

    public String transferFundsExpectingFailure(FundTransferData transferData) {
        fillTransferForm(transferData);
        click(submitButton);
        String alertText = readAlertTextAndAccept();
        if (!alertText.isBlank()) {
            return alertText;
        }
        return waitForAnyMessage(By.cssSelector("table table td"), By.tagName("body"));
    }

    private void fillTransferForm(FundTransferData transferData) {
        type(payerAccountField, transferData.payerAccount());
        type(payeeAccountField, transferData.payeeAccount());
        type(amountField, transferData.amount());
        type(descriptionField, transferData.description());
    }

    private String readTableValue(String label) {
        List<WebElement> rows = driver.findElements(resultRows);
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if (columns.size() >= 2 && columns.get(0).getText().trim().equalsIgnoreCase(label)) {
                return columns.get(1).getText().trim();
            }
        }
        return getText(By.cssSelector("p.heading3"));
    }
}
