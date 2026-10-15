package tests;

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
import java.util.List;
import java.util.Map;

public class ChromeWebTest {

    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setDeviceName("emulator-5554");
        options.setCapability("browserName", "Chrome");

        // Skip Chrome's first-run / default-browser screens
        options.setCapability("appium:chromeOptions", Map.of(
                "args", List.of("--no-first-run", "--disable-fre", "--no-default-browser-check")
        ));

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @Test
    public void openWebsiteInChromeShouldShowCorrectTitle() {

        driver.get("https://example.com");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.titleIs("Example Domain"));

        Assert.assertEquals(driver.getTitle(), "Example Domain", "Wrong page title");

        String heading = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(heading, "Example Domain", "Wrong heading text");

        System.out.println("PASS: Chrome opened " + driver.getCurrentUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}