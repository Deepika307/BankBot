package com.srm.bankbot.model;

public record FundTransferData(
        String payerAccount,
        String payeeAccount,
        String amount,
        String description) {
}
