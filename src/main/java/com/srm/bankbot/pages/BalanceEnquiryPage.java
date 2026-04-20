package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BalanceEnquiryPage extends BasePage {

    private final By accountNumberField = By.name("accountno");
    private final By submitButton = By.name("AccSubmit");
    private final By resultRows = By.cssSelector("table table tr");

    public BalanceEnquiryPage(WebDriver driver) {
        super(driver);
    }

    public String fetchBalance(String accountNumber) {
        type(accountNumberField, accountNumber);
        click(submitButton);
        waitForPageReady();
        skipIfPageUnavailable("Balance Enquiry page");
        return readTableValue("Balance");
    }

    private String readTableValue(String label) {
        for (int attempt = 0; attempt < 3; attempt++) {
            List<WebElement> rows = driver.findElements(resultRows);
            for (WebElement row : rows) {
                List<WebElement> columns = row.findElements(By.tagName("td"));
                if (columns.size() >= 2 && columns.get(0).getText().trim().equalsIgnoreCase(label)) {
                    return columns.get(1).getText().trim();
                }
            }
        }
        throw new IllegalStateException("Unable to read row with label: " + label);
    }
}
