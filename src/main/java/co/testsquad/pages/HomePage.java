package co.testsquad.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class HomePage extends BasePage {
    @AndroidFindBy(id = "welcome_text")
    @iOSXCUITFindBy(accessibility = "welcome_text")
    private WebElement welcomeText;

    public void verifyLoginSuccess() {
        waitForElement(welcomeText);
    }

    public void verifyHomeScreenDisplayed() {
        waitForElement(welcomeText);
    }
} 