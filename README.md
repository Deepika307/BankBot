# 🏦 BankBot – Selenium Automation Framework

## 📌 Overview

BankBot is a Selenium-based automation testing framework developed using Java, TestNG, and the Page Object Model (POM) design pattern.
It automates end-to-end test scenarios for a live online banking application.

This project was built as part of a hackathon to demonstrate skills in automation testing, framework design, and best practices.

---

## 🚀 Tech Stack

* **Language:** Java
* **Automation Tool:** Selenium WebDriver
* **Test Framework:** TestNG
* **Build Tool:** Maven
* **Design Pattern:** Page Object Model (POM)
* **Reporting:** Extent Reports 
* **Driver Management:** WebDriverManager

---

## 🌐 Application Under Test

* URL: https://demo.guru99.com/V4/index.php
* A demo banking application with modules like login, customer management, account management, and transactions.

---

## 📂 Project Structure

## 📂 Project Structure

```
BankBot/
│
├── src/
│   ├── main/java/com/srm/bankbot/
│   │   ├── base/
│   │   │   ├── BasePage.java
│   │   │   ├── BaseTest.java
│   │   │
│   │   ├── config/
│   │   │   ├── ConfigReader.java
│   │   │
│   │   ├── driver/
│   │   │   ├── DriverFactory.java
│   │   │
│   │   ├── listeners/
│   │   │   ├── RetryAnalyzer.java
│   │   │   ├── RetryTransformer.java
│   │   │   ├── TestListener.java
│   │   │
│   │   ├── model/
│   │   │   ├── AccountData.java
│   │   │   ├── Credentials.java
│   │   │   ├── CustomerData.java
│   │   │   ├── FundTransferData.java
│   │   │
│   │   ├── pages/
│   │   │   ├── AccessCredentialsPage.java
│   │   │   ├── BalanceEnquiryPage.java
│   │   │   ├── EditAccountPage.java
│   │   │   ├── EditCustomerPage.java
│   │   │   ├── FundTransferPage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── ManagerHomePage.java
│   │   │   ├── NewAccountPage.java
│   │   │   ├── NewCustomerPage.java
│   │   │
│   │   ├── reports/
│   │   │   ├── ExtentManager.java
│   │   │   ├── ExtentTestManager.java
│   │   │
│   │   ├── utils/
│   │   │   ├── CredentialService.java
│   │   │   ├── ScreenshotUtils.java
│   │   │   ├── TestDataFactory.java
│
│   ├── test/java/com/srm/bankbot/tests/
│   │   ├── AccountManagementTests.java
│   │   ├── AuthenticationTests.java
│   │   ├── CustomerManagementTests.java
│   │   ├── FormValidationTests.java
│   │   ├── FundTransferTests.java
│
│   ├── test/resources/
│   │   ├── config.properties
│
├── reports/
│   ├── extent-report.html
│
├── screenshots/
│   ├── (failure screenshots with timestamp)
│
├── test-output/
├── target/
├── pom.xml
├── testng.xml
```

---

## ✅ Features Implemented

### 🔐 Authentication Module

* Valid login verification
* Invalid login validation
* Logout functionality
* Blank input validation

### ⚙️ Framework Features

* Page Object Model (POM) architecture
* Data-driven testing using TestNG DataProvider
* Centralized configuration using `config.properties`
* Explicit wait strategy using `WebDriverWait`
* Screenshot capture on test failure 
* Reusable utility methods in BasePage

---

## 🧠 Key Design Principles

* Separation of concerns (Pages, Tests, Utilities)
* No hardcoded values (uses config file)
* Reusable and maintainable code structure
* Explicit waits instead of `Thread.sleep()`

---

## ⚙️ Configuration

Update `config.properties`:

```
browser=chrome
url=https://demo.guru99.com/V4/index.php
timeout=10
```

---

## ▶️ How to Run the Project

### 🔧 Prerequisites

* Java (JDK 8 or above)
* Maven
* IDE (Eclipse / IntelliJ)

### ▶️ Run Tests

 run via TestNG:

* Right-click `testng.xml` → Run

---

## 📊 Reporting

* Test results are generated using TestNG reports
* Extent Reports provide a detailed HTML report 
* Screenshots are captured on test failures and stored in `/screenshots/`

---

## 🚫 Best Practices Followed

* No use of `Thread.sleep()`
* No WebDriver code in test classes
* No hardcoded credentials
* Clean and modular framework design

---

## 🔮 Future Enhancements

* Parallel test execution
* Excel/JSON-based data providers
* Headless browser execution
* Retry mechanism for failed tests

---

## 👩‍💻 Author

**Deepika Kantheti**

---

## ⭐ Acknowledgment

This project was developed as part of a hackathon to demonstrate automation testing skills and framework design using Selenium and Java.

---
