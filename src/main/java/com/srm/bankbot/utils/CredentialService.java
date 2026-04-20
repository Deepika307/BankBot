package com.srm.bankbot.utils;

import com.srm.bankbot.config.ConfigReader;
import com.srm.bankbot.model.Credentials;

public final class CredentialService {

    private CredentialService() {
    }

    public static Credentials resolveManagerCredentials() {
        ConfigReader config = ConfigReader.getInstance();
        return new Credentials(
                config.getRequiredValue("manager.userId"),
                config.getRequiredValue("manager.password"));
    }
}
