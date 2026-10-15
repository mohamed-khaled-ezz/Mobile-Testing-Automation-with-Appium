# Mobile Testing Automation with Appium

Java-based mobile test automation project using **Appium, Selenium, TestNG, Maven, and Android Emulator**. The project covers Android application testing, mobile gestures, and Chrome browser automation.

## Project Overview

This project was created as part of a Mobile Testing Automation mini-task. It demonstrates how to configure an Appium environment and automate common mobile testing scenarios.

### Technologies Used

- Java
- Appium Server
- Appium Java Client
- Selenium WebDriver
- TestNG
- Apache Maven
- Android Studio and Android Emulator
- Android SDK and ADB
- Google Chrome on Android
- Git and GitHub

## Project Structure

The project follows a shared base-test approach to avoid duplicating driver initialization and common setup code.

```text
Demo/
├── pom.xml
├── README.md
├── APK/
│   └── app-debug.apk
├── src/
│   └── test/
│       └── java/
│           ├── org/
│           │   └── example/
│           │       └── Main.java
│           └── tests/
│               ├── BaseTest.java
│               ├── RegiTestAppTest.java
│               ├── ChromeWebTest.java
│               ├── DragAndDropGestureTest.java
│               ├── ScrollGestureTest.java
│               └── SlideGestureTest.java
└── target/
    └── surefire-reports/
```

> The structure above is a reference. Adjust file names and folders to match the actual files in your repository. Additional base classes or test classes may be present.

## Requirements

Install and configure the following tools before running the tests:

1. **Java JDK** compatible with the project's Maven and Gradle dependencies.
2. **Node.js and npm**.
3. **Appium Server**.
4. **Appium UiAutomator2 driver**.
5. **Android Studio** with Android SDK and an Android Virtual Device (AVD).
6. **Apache Maven**.
7. **Google Chrome** on the Android device for browser testing.
8. A physical Android device with USB debugging enabled, if testing on real hardware.

## Environment Setup

### 1. Verify Java and Maven

Open PowerShell and run:

```powershell
java -version
mvn -version
```

Both commands should display the installed versions.

### 2. Verify Node.js and npm

```powershell
node -v
npm -v
```

### 3. Install Appium

```powershell
npm install -g appium
```

Verify the installation:

```powershell
appium -v
```

### 4. Install the Android automation driver

```powershell
appium driver install uiautomator2
```

Verify installed drivers:

```powershell
appium driver list --installed
```

### 5. Configure Android SDK

Configure `ANDROID_HOME` to point to your Android SDK directory. Add the following directories to your Windows `Path` environment variable:

```text
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\emulator
```

Example SDK location:

```text
C:\Users\<YOUR_USERNAME>\AppData\Local\Android\Sdk
```

Verify that ADB is available:

```powershell
adb version
```

### 6. Verify the Appium environment

Run:

```powershell
appium-doctor --android
```

Review the output and resolve any missing requirements relevant to your setup.

### 7. Start Appium Server

```powershell
appium
```

The default server URL is:

```text
http://127.0.0.1:4723
```

If your project explicitly uses the `/wd/hub` endpoint, start Appium with the matching base path:

```powershell
appium --base-path /wd/hub
```

The server URL configured in the tests must match the URL used to start the server.

## Configure the Android Emulator

1. Open Android Studio.
2. Open **Device Manager**.
3. Create or start an Android Virtual Device.
4. Wait for the Android home screen to appear.
5. Verify the connection:

```powershell
adb devices
```

Example output:

```text
List of devices attached
emulator-5554    device
```

If you use a physical device, enable Developer Options and USB Debugging, connect it to the computer, and authorize the debugging prompt.

## Install Project Dependencies

Navigate to the project directory:

```powershell
cd D:\sprints_module_task\Demo
```

Download the Maven dependencies:

```powershell
mvn clean install
```

This command builds the project and runs the tests configured in Maven. If you only want to compile the test code without executing tests, use:

```powershell
mvn test-compile
```

## Running the Tests

Make sure the emulator or physical device and Appium Server are running before executing the tests.

### Run all configured tests

```powershell
mvn test
```

### Run an individual test class

Run the built-in application test:

```powershell
mvn -Dtest=RegiTestAppTest test
```

Run the Chrome browser test:

```powershell
mvn -Dtest=ChromeWebTest test
```

Run the Drag and Drop gesture test:

```powershell
mvn -Dtest=DragAndDropGestureTest test
```

Run the Scroll gesture test:

```powershell
mvn -Dtest=ScrollGestureTest test
```

Run the Slide gesture test:

```powershell
mvn -Dtest=SlideGestureTest test
```

Replace the class names in these commands if your actual test classes use different names.

## Test Scenarios

### 1. Built-in Android Application

The built-in application test demonstrates how to:

- Configure Android automation capabilities.
- Launch a built-in Android application.
- Locate an element using an appropriate locator strategy.
- Wait for the element to become visible.
- Interact with the element and verify the expected result.

### 2. APK Application Testing

The APK test demonstrates how to install and launch an Android application and interact with its interface.

Place the APK at the configured path. For example:

```text
APK/app-debug.apk
```

The test capabilities must reference the correct APK path, and the test must use locators that actually exist in that application.

**Important:** The APK must be the application intended by the test. A generic `app-debug.apk` cannot automatically be treated as the API Demos application. If a gesture test targets API Demos, provide the correct API Demos APK and configure its package and activity accordingly.

### 3. Mobile Gesture Automation

The project includes separate scenarios for mobile gestures:

| Gesture       | Purpose                                                        |
| ------------- | -------------------------------------------------------------- |
| Tap           | Taps a visible element and verifies the result.                |
| Scroll        | Scrolls through a screen or scrollable container.              |
| Slide         | Slides an element or control across the screen.                |
| Drag and Drop | Drags an element to a target location and verifies the result. |

Each gesture test should interact with an appropriate visible element and verify the resulting screen state or element position.

### 4. Chrome Web Testing

The Chrome test demonstrates how to:

- Start Chrome on an Android device.
- Configure browser automation capabilities.
- Open a web URL.
- Locate a web element.
- Interact with the element.
- Verify the expected browser behavior.

Chrome testing requires a compatible ChromeDriver for the installed Chrome version. If Appium reports a ChromeDriver compatibility error, configure a compatible driver or enable an appropriate Appium-managed ChromeDriver download strategy.

## Desired Capabilities

The following is an illustrative Java configuration for Android automation. Update the values to match your device, application, and installed Appium version.

```java
UiAutomator2Options options = new UiAutomator2Options();

options.setPlatformName("Android");
options.setDeviceName("Android Emulator");
options.setUdid("emulator-5554");
options.setAutomationName("UiAutomator2");
```

For APK testing, configure the actual APK file:

```java
options.setApp(
    "D:\\sprints_module_task\\Demo\\APK\\app-debug.apk"
);
```

For testing a built-in Android application, configure its actual package and launchable activity:

```java
options.setAppPackage("YOUR_APP_PACKAGE");
options.setAppActivity("YOUR_APP_ACTIVITY");
```

For Chrome browser testing, use Chrome-specific capabilities supported by your Appium Java Client version:

```java
options.setBrowserName("Chrome");
```

Use either the APK configuration or the built-in application configuration as appropriate for the test. Do not use placeholder package names in executable tests.

## Element Locator Strategies

Appium supports multiple locator strategies through Selenium's `By` class.

```java
// Locate an element by resource ID.
driver.findElement(By.id("com.example.app:id/login_button"));

// Locate an element by accessibility ID.
driver.findElement(AppiumBy.accessibilityId("Login"));

// Locate an element using XPath.
driver.findElement(By.xpath("//android.widget.Button[@text='Login']"));
```

Prefer resource IDs and accessibility IDs when available. Use XPath when other stable locators are unavailable.

Inspect the actual application using Appium Inspector to identify valid element attributes and locator values.

## Useful Driver Commands

```java
// Find an element.
WebElement element = driver.findElement(By.id("element_id"));

// Tap or click an element.
element.click();

// Enter text.
element.sendKeys("Test input");

// Read visible text.
String text = element.getText();

// Navigate back.
driver.navigate().back();

// Close the driver session.
driver.quit();
```

Use explicit waits when an element may take time to appear.

## Test Reports

After executing Maven tests, review the generated reports:

```text
target/surefire-reports/
```

The reports contain test results and failure details. Review the failure messages before marking a scenario as passed.

For a concise summary, Maven typically reports the number of tests run, failures, errors, and skipped tests in the terminal.

## Troubleshooting

### Appium cannot connect to the device

Check the emulator and ADB connection:

```powershell
adb devices
```

Confirm that Appium Server is running and that the configured server URL is correct.

### No such element

- Confirm that the correct application is open.
- Inspect the current screen with Appium Inspector.
- Verify the locator value.
- Add an explicit wait when necessary.

### APK file not found

Confirm that the configured APK path exists:

```powershell
Test-Path ".\APK\app-debug.apk"
```

If the test references another path, update the configuration or place the correct APK at that path.

### ChromeDriver version mismatch

Check the installed Chrome version and configure a compatible ChromeDriver. Do not assume a ChromeDriver for another major version will work.

### Tests fail during setup

Review the first error in the Maven output and the relevant report under `target/surefire-reports/`. A setup failure may prevent later test actions from running.

## GitHub

To upload the project to a GitHub repository:

```powershell
git init
git add .
git commit -m "Add Appium automation tests"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
git push -u origin main
```

Replace `YOUR_USERNAME` and `YOUR_REPOSITORY` with your actual GitHub username and repository name. If the repository already has a remote configured, do not add it a second time.

Avoid committing credentials, private configuration files, or unnecessary build artifacts.

## Completion Checklist

- [ ] Appium environment configured and verified.
- [ ] Built-in Android application test executed successfully.
- [ ] APK installation and interaction test executed successfully.
- [ ] Test executed on a physical Android device.
- [ ] Tap gesture implemented and verified.
- [ ] Scroll gesture implemented and verified.
- [ ] Slide gesture implemented and verified.
- [ ] Drag and Drop gesture implemented and verified.
- [ ] Chrome browser test executed successfully.
- [ ] Required evidence captured manually, where applicable.
- [ ] Appium Java Cheat Sheet saved as a Markdown or PDF file.
- [ ] README and test scripts uploaded to a public GitHub repository.

**Project status:** Update this checklist only after each scenario has actually been executed and verified. Do not mark a test as passed solely because its Java code compiles.

---

**Author:** Mohamed Khaled
**Project:** Mobile Testing Automation with Appium
**Language:** Java
**Build Tool:** Maven
#   M o b i l e - T e s t i n g - A u t o m a t i o n - w i t h - A p p i u m  
 