package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class EditCustomerPage extends BasePage {

    private final By customerIdField = By.name("cusid");
    private final By submitCustomerIdButton = By.name("AccSubmit");
    private final By addressField = By.name("addr");
    private final By cityField = By.name("city");
    private final By stateField = By.name("state");
    private final By submitUpdateButton = By.name("sub");
    private final By successMessage = By.cssSelector("p.heading3");

    public EditCustomerPage(WebDriver driver) {
        super(driver);
    }

    public EditCustomerPage loadCustomer(String customerId) {
        type(customerIdField, customerId);
        click(submitCustomerIdButton);
        waitForPageReady();
        skipIfPageUnavailable("Edit Customer page");
        return this;
    }

    public String updateAddress(String newAddress, String newCity, String newState) {
        type(addressField, newAddress);
        type(cityField, newCity);
        type(stateField, newState);
        click(submitUpdateButton);
        waitForPageReady();
        skipIfPageUnavailable("Edit Customer update page");
        return waitForAnyMessage(successMessage, By.tagName("body"));
    }

    public String getAddressValue() {
        return getValue(addressField);
    }
}
