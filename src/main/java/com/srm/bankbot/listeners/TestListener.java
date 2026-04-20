package com.srm.bankbot.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.srm.bankbot.driver.DriverFactory;
import com.srm.bankbot.reports.ExtentManager;
import com.srm.bankbot.reports.ExtentTestManager;
import com.srm.bankbot.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        ScreenshotUtils.cleanArtifactDirectories();
        ExtentManager.getExtentReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTestManager.startTest(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = null;
        try {
            driver = DriverFactory.getDriver();
        } catch (IllegalStateException ignored) {
        }

        String screenshotPath = driver == null ? "" : ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
        if (!screenshotPath.isBlank()) {
            ExtentTestManager.getTest().fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        } else {
            ExtentTestManager.getTest().fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTestManager.getTest().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentManager.flush();
    }
}
