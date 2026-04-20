package com.srm.bankbot.pages;

import com.srm.bankbot.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By userIdField = By.name("uid");
    private final By passwordField = By.name("password");
    private final By loginButton = By.name("btnLogin");
    private final By resetButton = By.name("btnReset");
    private final By hereLink = By.linkText("here");
    private final By marqueeText = By.cssSelector("marquee.heading3");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        waitForPageReady();
        return isDisplayed(userIdField) && isDisplayed(passwordField);
    }

    public ManagerHomePage login(String userId, String password) {
        type(userIdField, userId);
        type(passwordField, password);
        click(loginButton);
        return new ManagerHomePage(driver);
    }

    public String loginExpectingFailure(String userId, String password) {
        type(userIdField, userId);
        type(passwordField, password);
        click(loginButton);
        String alertText = readAlertTextAndAccept();
        return alertText.isBlank() ? getLoginErrorText() : alertText;
    }

    public String clickLoginWithBlankFields() {
        click(loginButton);
        String alertText = readAlertTextAndAccept();
        if (!alertText.isBlank()) {
            return alertText;
        }
        String browserMessage = getBrowserValidationMessage(userIdField);
        if (!browserMessage.isBlank()) {
            return browserMessage;
        }
        return getInlineValidationMessage(userIdField);
    }

    public void resetLoginForm() {
        click(resetButton);
    }

    public String getBannerMessage() {
        return getText(marqueeText);
    }

    public String getLoginErrorText() {
        return waitForAnyMessage(
                By.cssSelector("table table tr td:nth-child(2)"),
                By.cssSelector("body"),
                By.xpath("//p[contains(text(),'User or Password')]")
        );
    }

    public AccessCredentialsPage openCredentialGenerator() {
        click(hereLink);
        return new AccessCredentialsPage(driver);
    }
}
