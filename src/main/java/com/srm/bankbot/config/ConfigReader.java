package com.srm.bankbot.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final ConfigReader INSTANCE = new ConfigReader();
    private final Properties properties = new Properties();

    private ConfigReader() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Unable to find " + CONFIG_FILE + " on the classpath.");
            }
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load " + CONFIG_FILE, exception);
        }
    }

    public static ConfigReader getInstance() {
        return INSTANCE;
    }

    public String getBrowser() {
        return getValue("browser", "chrome");
    }

    public String getBaseUrl() {
        return getRequiredValue("base.url");
    }

    public Duration getTimeout() {
        return Duration.ofSeconds(Long.parseLong(getValue("timeout.seconds", "15")));
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getValue("headless", "false"));
    }

    public String getManagerUserId() {
        return getValue("manager.userId", "");
    }

    public String getManagerPassword() {
        return getValue("manager.password", "");
    }

    public String getCredentialEmailDomain() {
        return getValue("credential.email.domain", "examplemail.com");
    }

    public String getInvalidUserId() {
        return getValue("invalid.userId", "invalidUser");
    }

    public String getInvalidPassword() {
        return getValue("invalid.password", "invalidPass");
    }

    public String getValue(String key, String defaultValue) {
        return System.getProperty(key, properties.getProperty(key, defaultValue)).trim();
    }

    public String getRequiredValue(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalStateException("Missing required property: " + key);
        }
        return value.trim();
    }
}
