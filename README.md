# Web Automation Framework  

## 📌 Overview  
This project is a **UI automation testing framework** built using:  
- **Java** (Selenium WebDriver)  
- **TestNG** (test runner & parallel execution)  
- **Maven** (dependency & build management)  
- **ReportPortal** (real-time reporting & log aggregation)  
- **Logback** (logging & ReportPortal integration)  

It supports running tests in both **headless** and **UI** mode, with automatic screenshot capture on failure.  

---

## 🚀 Features  
- ✅ Page Object Model (POM) design pattern  
- ✅ Configurable headless / UI execution  
- ✅ Allure reporting with screenshot attachments  
- ✅ ReportPortal integration with log + screenshot support  
- ✅ Automatic screenshot on test failure  
- ✅ TestNG Listeners for reporting  
- ✅ CI/CD ready with Maven  :TBD

---

## 📂 Project Structure  

```
src/
 ├── main/
 │    └── java/
 │         ├── base/          # BaseTest, BasePage
 │         ├── pages/         # Page Objects
 │         ├── utils/         # Utilities (DriverFactory, ConfigReader, ReportPortalUtils, etc.)
 │         └── listeners/     # TestNG listeners (screenshot, ReportPortal, etc.)
 ├── test/
 │    └── java/
 │         └── tests/         # Test classes
 └── resources/
      ├── logback.xml         # ReportPortal logback appender
      ├── reportportal.properties   # ReportPortal configuration
      └── testng.xml          # TestNG suite
```

---

## ⚙️ Setup  

### 1️⃣ Prerequisites  
- JDK 11+  
- Maven 3.6+  
- Google Chrome (latest)  
- ChromeDriver (auto-managed by WebDriverManager)  

### 2️⃣ Install Dependencies  
```bash
mvn clean install
```

### 3️⃣ Run Tests  

👉 Run with UI mode:
```bash
mvn clean test -Dheadless=false
```

👉 Run in headless mode:
```bash
mvn clean test -Dheadless=true
```

---

## 📊 Reporting  

### 🔹 ReportPortal  
- Requires a running **ReportPortal server**  
- Configured via `logback.xml`  
- Screenshots & logs are automatically attached  

---
### 🔹 TestNG Emailable HTML Report
- By default, TestNG generates an HTML report located at `target/surefire-reports/emailable-report.html`

---

## 🖼️ Screenshots  

- Captured automatically on **test failure**  
- Attached to **ReportPortal**  


## 📦 CI/CD Integration  
- Can be integrated with **Jenkins**, **GitHub Actions**, or **GitLab CI**  
- Supports `mvn clean test` with headless execution for pipelines  

---

## 👨‍💻 Contributing  
1. Fork the repo  
2. Create a feature branch  
3. Commit changes  
4. Open a Pull Request  

---

## 📜 License  
MIT License – feel free to use & modify  
