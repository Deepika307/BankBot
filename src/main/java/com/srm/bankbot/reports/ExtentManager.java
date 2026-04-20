package com.srm.bankbot.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ExtentManager {

    private static ExtentReports extentReports;

    private ExtentManager() {
    }

    public static synchronized ExtentReports getExtentReports() {
        if (extentReports == null) {
            try {
                Path reportDirectory = Paths.get("reports");
                Files.createDirectories(reportDirectory);
                ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportDirectory.resolve("extent-report.html").toString());
                sparkReporter.config().setReportName("BankBot Automation Report");
                sparkReporter.config().setDocumentTitle("Guru99 Bank Test Execution");
                extentReports = new ExtentReports();
                extentReports.attachReporter(sparkReporter);
                extentReports.setSystemInfo("Framework", "Selenium + TestNG + POM");
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to initialize ExtentReports.", exception);
            }
        }
        return extentReports;
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
