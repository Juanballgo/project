package co.com.electromovil.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegistroPage extends BasePage {
    @FindBy(name = "name")
    private WebElement nameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "phone")
    private WebElement phoneInput;

    @FindBy(name = "address")
    private WebElement addressInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(name = "password_confirmation")
    private WebElement confirmPasswordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement registerButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    public RegistroPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToRegistro() {
        driver.get(BASE_URL + "/register");
    }

    public void fillRegistrationForm(String name, String email, String phone, String address, String password, String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOf(nameInput)).sendKeys(name);
        emailInput.sendKeys(email);
        phoneInput.sendKeys(phone);
        addressInput.sendKeys(address);
        passwordInput.sendKeys(password);
        confirmPasswordInput.sendKeys(confirmPassword);
    }

    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOf(errorMessage)).getText();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).getText();
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("/login");
    }
}