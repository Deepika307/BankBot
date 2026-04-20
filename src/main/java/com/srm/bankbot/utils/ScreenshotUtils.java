package com.srm.bankbot.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final Path SCREENSHOT_DIRECTORY = Paths.get("screenshots");
    private static final Path REPORT_DIRECTORY = Paths.get("reports");

    private ScreenshotUtils() {
    }

    public static String capture(WebDriver driver, String testName) {
        try {
            Files.createDirectories(SCREENSHOT_DIRECTORY);
            Path destination = SCREENSHOT_DIRECTORY.resolve(testName + "_" + LocalDateTime.now().format(FORMATTER) + ".png");
            Path source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE).toPath();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            return destination.toString();
        } catch (IOException exception) {
            return "";
        }
    }

    public static void cleanArtifactDirectories() {
        cleanDirectory(SCREENSHOT_DIRECTORY);
        cleanDirectory(REPORT_DIRECTORY);
    }

    private static void cleanDirectory(Path directory) {
        try {
            if (!Files.exists(directory)) {
                return;
            }
            Files.walk(directory)
                    .sorted((left, right) -> right.compareTo(left))
                    .filter(path -> !path.equals(directory))
                    .forEach(ScreenshotUtils::deletePathQuietly);
        } catch (IOException ignored) {
        }
    }

    private static void deletePathQuietly(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException ignored) {
        }
    }
}
