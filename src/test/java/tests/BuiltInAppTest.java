package tests;

import org.testng.annotations.Test;

public class BuiltInAppTest extends BaseTest {

    @Test
    public void inspectCurrentScreen() throws Exception {

        System.out.println("Current package: " + driver.getCurrentPackage());
        System.out.println("Current activity: " + driver.currentActivity());

        String pageSource = driver.getPageSource();

        System.out.println("===== APP SCREEN SOURCE =====");
        System.out.println(pageSource);
        System.out.println("===== END SCREEN SOURCE =====");

    }
}