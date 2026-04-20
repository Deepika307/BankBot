package com.srm.bankbot.model;

public record CustomerData(
        String customerName,
        String gender,
        String dateOfBirth,
        String address,
        String city,
        String state,
        String pin,
        String mobileNumber,
        String email,
        String password) {
}
