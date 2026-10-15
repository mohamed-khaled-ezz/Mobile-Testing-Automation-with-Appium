package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class SlideGestureTest extends ApiDemosBaseTest {

    // ID locators: resource ids taken from Appium Inspector
    private static final By SEEK_BAR = AppiumBy.id("io.appium.android.apis:id/seek");
    private static final By PROGRESS_TEXT = AppiumBy.id("io.appium.android.apis:id/progress");

    @Test
    public void slidingSeekBarToTheRightShouldChangeProgress() {
        openViewsScreen("Seek Bar");

        WebElement seekBar = waitForVisible(SEEK_BAR);
        String progressBefore = driver.findElement(PROGRESS_TEXT).getText();

        // Slide from 10% to 90% of the bar width along its vertical center
        Rectangle bar = seekBar.getRect();
        int centerY = bar.y + bar.height / 2;
        gesture("dragGesture", Map.of(
                "startX", bar.x + bar.width / 10,
                "startY", centerY,
                "endX", bar.x + bar.width * 9 / 10,
                "endY", centerY));

        Assert.assertNotEquals(driver.findElement(PROGRESS_TEXT).getText(), progressBefore,
                "Progress value did not change after sliding");
    }
}
