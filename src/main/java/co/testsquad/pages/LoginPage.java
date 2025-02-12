package co.testsquad.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    @AndroidFindBy(id = "username_field")
    @iOSXCUITFindBy(accessibility = "username_field")
    private WebElement usernameField;

    @AndroidFindBy(id = "password_field")
    @iOSXCUITFindBy(accessibility = "password_field")
    private WebElement passwordField;

    @AndroidFindBy(id = "login_button")
    @iOSXCUITFindBy(accessibility = "login_button")
    private WebElement loginButton;

    public void verifyLoginPageDisplayed() {
        waitForElement(usernameField);
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void tapLoginButton() {
        loginButton.click();
    }
} 