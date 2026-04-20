package com.srm.bankbot.base;

import com.srm.bankbot.config.ConfigReader;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.SkipException;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Duration timeout;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.timeout = ConfigReader.getInstance().getTimeout();
        this.wait = new WebDriverWait(driver, timeout);
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element
        );
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void type(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        scrollToElement(element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
        element.sendKeys(text);
    }

    protected void append(By locator, String text) {
        waitForVisibility(locator).sendKeys(text);
    }

    protected void clear(By locator) {
        WebElement element = waitForVisibility(locator);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
    }

    protected void click(By locator) {
        WebElement element = waitForClickable(locator);
        scrollToElement(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException exception) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void selectByVisibleText(By locator, String text) {
        new Select(waitForVisibility(locator)).selectByVisibleText(text);
    }

    protected String getText(By locator) {
        return waitForVisibility(locator).getText().trim();
    }

    protected String getValue(By locator) {
        return waitForVisibility(locator).getDomProperty("value");
    }

    protected boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    protected boolean isPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (NoSuchElementException exception) {
            return false;
        }
    }

    protected void waitForUrlContains(String value) {
        wait.until(ExpectedConditions.urlContains(value));
    }

    protected void waitForPageReady() {
        wait.until((ExpectedCondition<Boolean>) webDriver ->
                "complete".equals(((JavascriptExecutor) webDriver).executeScript("return document.readyState")));
    }

    protected boolean isServerErrorPage() {
        String pageSource = driver.getPageSource();
        String title = driver.getTitle();
        return pageSource.contains("HTTP ERROR 500")
                || pageSource.contains("This page isn&apos;t working")
                || pageSource.contains("This page isn't working")
                || title.contains("HTTP ERROR 500");
    }

    protected boolean isBlankPage() {
        String pageSource = driver.getPageSource().replaceAll("\\s+", "");
        return pageSource.equals("<html><head></head><body></body></html>")
                || pageSource.equals("<html><head></head><body></body></html><!--?xml:namespaceprefix=ons=\"urn:schemas-microsoft-com:office:office\"/?-->");
    }

    protected void skipIfPageUnavailable(String pageName) {
        if (isServerErrorPage() || isBlankPage()) {
            throw new SkipException(pageName + " is unavailable on the live AUT right now.");
        }
    }

    protected String readAlertTextAndAccept() {
        try {
            String text = wait.until(ExpectedConditions.alertIsPresent()).getText();
            driver.switchTo().alert().accept();
            return text;
        } catch (TimeoutException | NoAlertPresentException exception) {
            return "";
        }
    }

    protected String getInlineValidationMessage(By fieldLocator) {
        WebElement field = waitForVisibility(fieldLocator);
        String describedBy = field.getAttribute("aria-describedby");
        if (describedBy != null && !describedBy.isBlank()) {
            for (String id : describedBy.split(" ")) {
                String text = driver.findElement(By.id(id)).getText().trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
        }

        List<By> fallbacks = List.of(
                By.xpath("//label[contains(@id,'message') and @for='" + field.getAttribute("name") + "']"),
                By.xpath("//span[contains(@id,'message') and @for='" + field.getAttribute("name") + "']"),
                By.xpath("//td/input[@name='" + field.getAttribute("name") + "']/following::label[contains(@id,'message')][1]")
        );

        for (By fallback : fallbacks) {
            List<WebElement> matches = driver.findElements(fallback);
            if (!matches.isEmpty()) {
                String text = matches.get(0).getText().trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
        }
        return "";
    }

    protected String getBrowserValidationMessage(By fieldLocator) {
        WebElement field = waitForVisibility(fieldLocator);
        Object message = ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", field);
        return message == null ? "" : message.toString().trim();
    }

    protected void blur(By locator) {
        waitForVisibility(locator).sendKeys(Keys.TAB);
    }

    protected void setValueUsingJavaScript(By locator, String value) {
        WebElement element = waitForVisibility(locator);
        scrollToElement(element);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                element,
                value
        );
    }

    protected String waitForAnyMessage(By... locators) {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(timeout)
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return fluentWait.until(webDriver -> {
            for (By locator : locators) {
                List<WebElement> matches = webDriver.findElements(locator);
                if (!matches.isEmpty()) {
                    String text = matches.get(0).getText().trim();
                    if (!text.isEmpty()) {
                        return text;
                    }
                }
            }
            return null;
        });
    }
}
