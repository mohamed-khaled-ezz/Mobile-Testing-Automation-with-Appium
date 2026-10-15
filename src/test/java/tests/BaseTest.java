
package tests;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {

        UiAutomator2Options options = new UiAutomator2Options();

        // Connect to the running Android emulator.
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");

        // Launch the built-in Android Settings app.
        options.setAppPackage("com.android.settings");
        options.setAppActivity("com.android.settings.Settings");
        options.setNoReset(true);

        // Connect to the existing Appium server.
        driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723/wd/hub"),
                options
        );

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
