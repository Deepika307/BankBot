package com.srm.bankbot.reports;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentTestManager {

    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    private ExtentTestManager() {
    }

    public static synchronized void startTest(String testName) {
        EXTENT_TEST.set(ExtentManager.getExtentReports().createTest(testName));
    }

    public static ExtentTest getTest() {
        return EXTENT_TEST.get();
    }
}
