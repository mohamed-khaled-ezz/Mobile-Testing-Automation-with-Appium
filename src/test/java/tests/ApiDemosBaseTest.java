package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;

import java.nio.file.Paths;

/** Shared setup for the gesture tests, which all run against the ApiDemos sample app. */
public abstract class ApiDemosBaseTest extends BaseTest {

    // Accessibility ID locator: main menu entry that holds the widget demos
    protected static final By VIEWS_MENU = AppiumBy.accessibilityId("Views");

    @Override
    protected UiAutomator2Options buildOptions() {
        UiAutomator2Options options = baseOptions();

        // Appium installs and launches ApiDemos (change with -Dapidemos.apk.path=...)
        String apkPath = System.getProperty("apidemos.apk.path", "apps/ApiDemos-debug.apk");
        options.setApp(Paths.get(apkPath).toAbsolutePath().toString());
        return options;
    }

    /** Opens Views, then scrolls the list until the given item is visible and opens it. */
    protected void openViewsScreen(String itemText) {
        waitForVisible(VIEWS_MENU).click();

        // UiAutomator locator: scrolls the list until an item with this exact text is on screen
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                        + ".scrollIntoView(new UiSelector().text(\"" + itemText + "\"))")).click();
    }
}
