package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class RegiTestAppTest {

    private static final By TITLE = AppiumBy.accessibilityId("Create Account");
    private static final By CHECKBOX = AppiumBy.className("android.widget.CheckBox");

    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");
        options.setAppPackage("com.example.regitest_app");
        options.setAppActivity("com.example.regitest_app.MainActivity");
        options.setNoReset(true);
        options.setCapability("appium:forceAppLaunch", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Wait until the registration screen is rendered
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(TITLE));
    }

    @Test
    public void termsCheckboxShouldToggleWhenClicked() {

        Assert.assertEquals(driver.findElement(CHECKBOX).getAttribute("checked"), "false",
                "Checkbox should be unchecked by default");

        driver.findElement(CHECKBOX).click();
        Assert.assertEquals(driver.findElement(CHECKBOX).getAttribute("checked"), "true",
                "Checkbox should be checked after the first click");

        driver.findElement(CHECKBOX).click();
        Assert.assertEquals(driver.findElement(CHECKBOX).getAttribute("checked"), "false",
                "Checkbox should be unchecked after the second click");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}