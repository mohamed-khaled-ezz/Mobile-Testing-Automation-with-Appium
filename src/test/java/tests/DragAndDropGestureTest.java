package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class DragAndDropGestureTest extends ApiDemosBaseTest {

    // ID locators: resource ids taken from Appium Inspector
    private static final By SOURCE_DOT = AppiumBy.id("io.appium.android.apis:id/drag_dot_1");
    private static final By TARGET_DOT = AppiumBy.id("io.appium.android.apis:id/drag_dot_2");
    private static final By RESULT_TEXT = AppiumBy.id("io.appium.android.apis:id/drag_result_text");

    @Test
    public void draggingFirstDotOntoSecondDotShouldShowDroppedMessage() {
        openViewsScreen("Drag and Drop");

        // Drop point = center of the target dot
        Rectangle target = waitForVisible(TARGET_DOT).getRect();
        gesture("dragGesture", Map.of(
                "elementId", idOf(waitForVisible(SOURCE_DOT)),
                "endX", target.x + target.width / 2,
                "endY", target.y + target.height / 2));

        Assert.assertTrue(driver.findElement(RESULT_TEXT).getText().contains("Dropped"),
                "Drop result message was not shown");
    }
}
