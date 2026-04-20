package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import com.srm.bankbot.model.CustomerData;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NewCustomerPage extends BasePage {

    private final By nameField = By.name("name");
    private final By maleGenderRadio = By.cssSelector("input[value='m']");
    private final By femaleGenderRadio = By.cssSelector("input[value='f']");
    private final By dateOfBirthField = By.name("dob");
    private final By addressField = By.name("addr");
    private final By cityField = By.name("city");
    private final By stateField = By.name("state");
    private final By pinField = By.name("pinno");
    private final By mobileNumberField = By.name("telephoneno");
    private final By emailField = By.name("emailid");
    private final By passwordField = By.name("password");
    private final By submitButton = By.name("sub");
    private final By resetButton = By.name("res");
    private final By customerTableRows = By.cssSelector("table table tr");

    public NewCustomerPage(WebDriver driver) {
        super(driver);
    }

    public String createCustomer(CustomerData customerData) {
        fillCustomerForm(customerData);
        click(submitButton);
        waitForPageReady();
        return readTableValue("Customer ID");
    }

    public void fillCustomerForm(CustomerData customerData) {
        setValueUsingJavaScript(nameField, customerData.customerName());
        chooseGender(customerData.gender());
        setDateOfBirth(customerData.dateOfBirth());
        setValueUsingJavaScript(addressField, customerData.address());
        setValueUsingJavaScript(cityField, customerData.city());
        setValueUsingJavaScript(stateField, customerData.state());
        setValueUsingJavaScript(pinField, customerData.pin());
        setValueUsingJavaScript(mobileNumberField, customerData.mobileNumber());
        setValueUsingJavaScript(emailField, customerData.email());
        setValueUsingJavaScript(passwordField, customerData.password());
    }

    public String createCustomerExpectingFailure(CustomerData customerData) {
        fillCustomerForm(customerData);
        click(submitButton);
        String alertText = readAlertTextAndAccept();
        if (!alertText.isBlank()) {
            return alertText;
        }
        return waitForAnyMessage(
                By.cssSelector("table table td"),
                By.xpath("//p[contains(text(),'Email Address Already Exist')]"),
                By.xpath("//table//td[contains(text(),'Email Address Already Exist')]")
        );
    }

    public String getSuccessHeading() {
        return waitForAnyMessage(
                By.cssSelector("p.heading3"),
                By.xpath("//p[contains(text(),'Customer Registered Successfully')]")
        );
    }

    public String getValidationMessageForNameField() {
        blur(nameField);
        String inlineMessage = getInlineValidationMessage(nameField);
        return inlineMessage.isBlank() ? getBrowserValidationMessage(nameField) : inlineMessage;
    }

    public String getValidationMessageForPinFieldAfterAlphaInput() {
        type(pinField, "abcd");
        blur(pinField);
        String inlineMessage = getInlineValidationMessage(pinField);
        return inlineMessage.isBlank() ? getValue(pinField) : inlineMessage;
    }

    public String getFutureDobValidationMessage() {
        setDateOfBirth("2099-12-31");
        blur(dateOfBirthField);
        String inlineMessage = getInlineValidationMessage(dateOfBirthField);
        if (!inlineMessage.isBlank()) {
            return inlineMessage;
        }
        String browserMessage = getBrowserValidationMessage(dateOfBirthField);
        if (!browserMessage.isBlank()) {
            return browserMessage;
        }
        return getValue(dateOfBirthField);
    }

    public void resetForm() {
        click(resetButton);
    }

    private void chooseGender(String gender) {
        if ("female".equalsIgnoreCase(gender)) {
            click(femaleGenderRadio);
        } else {
            click(maleGenderRadio);
        }
    }

    private void setDateOfBirth(String value) {
        setValueUsingJavaScript(dateOfBirthField, value);
    }

    private String readTableValue(String label) {
        List<WebElement> rows = driver.findElements(customerTableRows);
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            if (columns.size() >= 2 && columns.get(0).getText().trim().equalsIgnoreCase(label)) {
                return columns.get(1).getText().trim();
            }
        }
        throw new IllegalStateException("Unable to read row with label: " + label);
    }
}
