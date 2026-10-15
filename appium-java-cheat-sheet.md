# Appium Java Cheat Sheet

A quick reference guide for mobile automation using Appium, Java, Selenium WebDriver, TestNG, and Android UiAutomator2.

## 1. Start Appium Server

Start the Appium server using PowerShell:

```powershell
appium
```

Default server URL:

```text
http://127.0.0.1:4723/
```

Install the Android automation driver:

```powershell
appium driver install uiautomator2
```

Check installed drivers:

```powershell
appium driver list --installed
```

Check connected Android devices:

```powershell
adb devices
```

---

## 2. Desired Capabilities

Desired capabilities configure the device and application before starting an Appium session. Modern Appium Java Client versions use options classes such as `UiAutomator2Options`.

### Android Emulator Configuration

```java
import io.appium.java_client.android.options.UiAutomator2Options;

UiAutomator2Options options = new UiAutomator2Options();

options.setPlatformName("Android");
options.setAutomationName("UiAutomator2");
options.setDeviceName("Android Emulator");
options.setUdid("emulator-5554");
```

### Important Capabilities

| Capability | Description |
|---|---|
| `platformName` | Target platform, such as Android |
| `automationName` | Automation engine, such as UiAutomator2 |
| `deviceName` | Device name |
| `udid` | Unique device identifier |
| `app` | Path to the APK file |
| `appPackage` | Android application package |
| `appActivity` | Activity to launch |
| `browserName` | Browser name, such as Chrome |

---

## 3. Connect to the Android Device

Use `AndroidDriver` to create an Appium session.

```java
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;

UiAutomator2Options options = new UiAutomator2Options()
        .setPlatformName("Android")
        .setAutomationName("UiAutomator2")
        .setDeviceName("Android Emulator");

AndroidDriver driver =
        new AndroidDriver(
                new URL("http://127.0.0.1:4723/"),
                options
        );
```

For a physical Android device, set its actual ID:

```java
options.setUdid("YOUR_DEVICE_ID");
```

Find the ID using:

```powershell
adb devices
```

---

## 4. Element Locator Strategies

Appium supports different strategies to locate elements on the screen.

### Locate by ID

```java
driver.findElement(
        By.id("com.example.app:id/login_button")
);
```

### Locate by Accessibility ID

```java
driver.findElement(
        AppiumBy.accessibilityId("Login")
);
```

### Locate by XPath

```java
driver.findElement(
        By.xpath("//android.widget.Button[@text='Login']")
);
```

Required imports:

```java
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
```

**Best practices:**

- Prefer stable resource IDs.
- Use Accessibility IDs when available.
- Use Appium Inspector to identify valid locators.
- Avoid complicated XPath expressions when a stable locator exists.

---

## 5. Common Element Actions

### Find an Element

```java
WebElement element = driver.findElement(
        By.id("com.example.app:id/login_button")
);
```

### Click an Element

```java
element.click();
```

### Enter Text

```java
driver.findElement(
        By.id("com.example.app:id/username")
).sendKeys("test_user");
```

### Read Text

```java
String text = element.getText();
```

### Check Visibility

```java
boolean visible = element.isDisplayed();
```

### Clear a Text Field

```java
element.clear();
```

Required import:

```java
import org.openqa.selenium.WebElement;
```

---

## 6. Explicit Waits

Explicit waits allow Appium to wait for an element to become available instead of trying to interact with it immediately.

```java
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

WebDriverWait wait = new WebDriverWait(
        driver,
        Duration.ofSeconds(10)
);

WebElement loginButton = wait.until(
        ExpectedConditions.elementToBeClickable(
                By.id("com.example.app:id/login_button")
        )
);

loginButton.click();
```

Use explicit waits for screens and elements that load asynchronously.

---

## 7. Common Driver Commands

### Navigate Back

```java
driver.navigate().back();
```

### Get Current URL

```java
String currentUrl = driver.getCurrentUrl();
```

This is useful when testing a browser session.

### Get Page Title

```java
String title = driver.getTitle();
```

### Take a Screenshot

```java
import org.openqa.selenium.OutputType;
import java.io.File;

File screenshot = driver.getScreenshotAs(OutputType.FILE);
```

This returns a screenshot file object. Copy it to a destination path if you need to save the evidence.

---

## 8. Mobile Gestures

Gesture implementations can vary according to the Appium Java Client and Selenium versions. The examples below illustrate common approaches.

### 8.1 Tap

For a visible button, use its locator:

```java
driver.findElement(
        AppiumBy.accessibilityId("Open menu")
).click();
```

This is generally more reliable than using fixed screen coordinates.

### 8.2 Scroll

For an Android screen containing a scrollable view:

```java
driver.findElement(
        AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                + ".scrollForward()"
        )
);
```

This uses Android UiAutomator selectors. The screen must contain a suitable scrollable container.

### 8.3 Slide or Swipe

A swipe can be performed using Selenium's W3C Actions API.

```java
import java.time.Duration;
import java.util.Collections;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

PointerInput finger = new PointerInput(
        PointerInput.Kind.TOUCH,
        "finger"
);

Sequence swipe = new Sequence(finger, 1);

swipe.addAction(
        finger.createPointerMove(
                Duration.ZERO,
                PointerInput.Origin.viewport(),
                500, 1500
        )
);

swipe.addAction(
        finger.createPointerDown(
                PointerInput.MouseButton.LEFT.asArg()
        )
);

swipe.addAction(
        finger.createPointerMove(
                Duration.ofMillis(700),
                PointerInput.Origin.viewport(),
                500, 500
        )
);

swipe.addAction(finger.createPointerUp(
        PointerInput.MouseButton.LEFT.asArg()
));

driver.perform(Collections.singletonList(swipe));
```

The coordinates are examples. Adjust them to the actual device screen and target control.

### 8.4 Drag and Drop

A drag-and-drop gesture moves an element from a source position to a destination position.

The general sequence is:

1. Move the pointer to the source.
2. Press and hold.
3. Move to the destination.
4. Release.
5. Verify that the item moved or the expected state changed.

Use the W3C Actions API or the gesture API supported by your installed Appium Java Client version. For reliable tests, determine the source and destination positions from the actual screen rather than assuming fixed coordinates.

---

## 9. APK Testing

To launch an application from an APK file, configure its path:

```java
options.setApp(
        "D:\\sprints_module_task\\Demo\\APK\\app-debug.apk"
);
```

Check that the file exists:

```powershell
Test-Path ".\APK\app-debug.apk"
```

Alternatively, launch an already-installed application using its real package and activity:

```java
options.setAppPackage("YOUR_APP_PACKAGE");
options.setAppActivity("YOUR_APP_ACTIVITY");
```

Replace the placeholders with the actual application values.

---

## 10. Chrome Web Testing

To automate Chrome on an Android device, configure the browser session:

```java
UiAutomator2Options options = new UiAutomator2Options()
        .setPlatformName("Android")
        .setAutomationName("UiAutomator2")
        .setDeviceName("Android Emulator")
        .setBrowserName("Chrome");
```

Create the driver and open a website:

```java
AndroidDriver driver = new AndroidDriver(
        new URL("http://127.0.0.1:4723/"),
        options
);

driver.get("https://example.com");

String title = driver.getTitle();

System.out.println(title);
```

### Interact with a Web Element

```java
WebElement link = driver.findElement(
        By.cssSelector("a")
);

link.click();
```

**Important:** Chrome must be installed on the Android device. The ChromeDriver setup must be compatible with the installed Chrome version.

Use CSS selectors for web page elements. Use Android locators for native application elements.

---

## 11. TestNG Basics

TestNG is used to organize and execute Java test methods.

### Example Test

```java
import org.testng.Assert;
import org.testng.annotations.Test;

public class ExampleTest {

    @Test
    public void welcomeMessageShouldBeCorrect() {
        String actual = "Welcome";

        Assert.assertEquals(actual, "Welcome");
    }
}
```

### Common Assertions

Verify that an element is visible:

```java
Assert.assertTrue(element.isDisplayed());
```

Verify the expected text:

```java
Assert.assertEquals(actualText, expectedText);
```

In real tests, assert values obtained from the application instead of hard-coded demonstration values.

---

## 12. Base Test Class and Cleanup

A shared `BaseTest` class can initialize the driver before each test and close it afterward.

Example cleanup method:

```java
import org.testng.annotations.AfterMethod;

@AfterMethod
public void tearDown() {
    if (driver != null) {
        driver.quit();
    }
}
```

This assumes that `driver` is a class field initialized during setup.

Always close the driver session after testing to release device and server resources.

---

## 13. Troubleshooting

| Problem | Possible Solution |
|---|---|
| Device not detected | Run `adb devices` and check USB debugging. |
| Appium session fails | Verify the server URL, UiAutomator2 driver, and capabilities. |
| `NoSuchElementException` | Inspect the screen and verify the locator with Appium Inspector. |
| APK not found | Check the APK path and file name. |
| Chrome session fails | Check Chrome installation and ChromeDriver compatibility. |
| Maven tests fail | Inspect the terminal output and `target/surefire-reports/`. |

### Useful Commands

```powershell
appium
appium driver list --installed
adb devices
mvn clean test
mvn -Dtest=ChromeWebTest test
```

---

## 14. Important Reminders

- Replace example package names, IDs, device IDs, and file paths with actual values.
- Check compatibility with the versions configured in `pom.xml`.
- Use Appium Inspector to find reliable locators.
- Prefer explicit waits over fixed delays.
- Verify each gesture's result with an assertion.
- Capture screenshots only when the test actually runs.
- A successful compilation does not necessarily mean a test passed.


