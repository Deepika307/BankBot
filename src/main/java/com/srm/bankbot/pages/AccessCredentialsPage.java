package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import com.srm.bankbot.model.Credentials;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AccessCredentialsPage extends BasePage {

    private final By emailField = By.name("emailid");
    private final By submitButton = By.name("btnLogin");
    private final By credentialRows = By.cssSelector("table table tr");

    public AccessCredentialsPage(WebDriver driver) {
        super(driver);
    }

    public Credentials generateFreshCredentials(String email) {
        type(emailField, email);
        click(submitButton);
        waitForPageReady();
        return new Credentials(readTableValue("User ID"), readTableValue("Password"));
    }

    private String readTableValue(String rowLabel) {
        List<WebElement> rows = driver.findElements(credentialRows);
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if (columns.size() >= 2 && columns.get(0).getText().trim().equalsIgnoreCase(rowLabel)) {
                return columns.get(1).getText().trim();
            }
        }
        throw new IllegalStateException("Unable to locate generated value for " + rowLabel);
    }
}
