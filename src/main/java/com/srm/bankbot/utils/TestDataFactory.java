package com.srm.bankbot.utils;

import com.srm.bankbot.config.ConfigReader;
import com.srm.bankbot.model.AccountData;
import com.srm.bankbot.model.CustomerData;
import com.srm.bankbot.model.FundTransferData;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class TestDataFactory {

    private static final DateTimeFormatter DOB_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private TestDataFactory() {
    }

    public static String uniqueValue(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    public static String uniqueEmail() {
        String millis = String.valueOf(System.currentTimeMillis());
        String localPart = "bb" + millis.substring(millis.length() - 6);
        return localPart + "@" + ConfigReader.getInstance().getCredentialEmailDomain();
    }

    public static CustomerData validCustomer() {
        return new CustomerData(
                "Bankbotuser",
                "male",
                LocalDate.of(1996, 4, 18).format(DOB_FORMATTER),
                "Automation Street",
                "Chennai",
                "TamilNadu",
                "600001",
                "9876543210",
                uniqueEmail(),
                "SecurePass123");
    }

    public static CustomerData customerWithEmail(String email) {
        CustomerData base = validCustomer();
        return new CustomerData(
                base.customerName(),
                base.gender(),
                base.dateOfBirth(),
                base.address(),
                base.city(),
                base.state(),
                base.pin(),
                base.mobileNumber(),
                email,
                base.password());
    }

    public static AccountData validAccount(String customerId) {
        return new AccountData(customerId, "Savings", "5000");
    }

    public static FundTransferData validTransfer(String payerAccount, String payeeAccount) {
        return new FundTransferData(payerAccount, payeeAccount, "500", "Hackathon transfer");
    }
}
