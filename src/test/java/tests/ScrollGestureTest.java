
package tests;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ScrollGestureTest extends ApiDemosBaseTest {

    private static final int MAX_SCROLL_ATTEMPTS = 10;

    private static final By FIRST_ITEM =
            AppiumBy.accessibilityId("Animation");

    private static final By LAST_ITEM =
            AppiumBy.accessibilityId("Visibility");

    @Test
    public void scrollingDownViewsListShouldRevealLastItem() {
        waitForVisible(VIEWS_MENU).click();
        waitForVisible(FIRST_ITEM);

        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        Assert.assertTrue(
                driver.findElements(LAST_ITEM).isEmpty(),
                "Last item should not be visible before scrolling"
        );

        Dimension screen = driver.manage().window().getSize();

        for (int attempt = 0;
             attempt < MAX_SCROLL_ATTEMPTS
                     && driver.findElements(LAST_ITEM).isEmpty();
             attempt++) {

            Map<String, Object> arguments = new HashMap<>();
            arguments.put("left", screen.width / 10);
            arguments.put("top", screen.height / 4);
            arguments.put("width", screen.width * 8 / 10);
            arguments.put("height", screen.height / 2);
            arguments.put("direction", "down");
            arguments.put("percent", 0.75);

            driver.executeScript("mobile: scrollGesture", arguments);
        }

        Assert.assertTrue(
                driver.findElement(LAST_ITEM).isDisplayed(),
                "Last item did not appear after scrolling"
        );
    }
}
