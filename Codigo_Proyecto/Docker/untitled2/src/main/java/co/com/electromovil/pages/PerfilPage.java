package co.com.electromovil.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PerfilPage extends BasePage {
    @FindBy(name = "name")
    private WebElement nameInput;

    @FindBy(name = "phone")
    private WebElement phoneInput;

    @FindBy(name = "address")
    private WebElement addressInput;

    @FindBy(name = "current_password")
    private WebElement currentPasswordInput;

    @FindBy(name = "new_password")
    private WebElement newPasswordInput;

    @FindBy(name = "new_password_confirmation")
    private WebElement confirmPasswordInput;

    @FindBy(css = "button[data-testid='save-profile']")
    private WebElement saveProfileButton;

    @FindBy(css = "button[data-testid='change-password']")
    private WebElement changePasswordButton;

    @FindBy(css = "button[data-testid='save-password']")
    private WebElement savePasswordButton;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    @FindBy(css = ".mis-electrodomesticos")
    private WebElement electrodomesticosSection;

    @FindBy(css = "button[data-testid='add-appliance']")
    private WebElement addApplianceButton;

    public PerfilPage(WebDriver driver) {
        super(driver);
    }

    public void updateProfile(String name, String phone, String address) {
        clearAndType(nameInput, name);
        clearAndType(phoneInput, phone);
        clearAndType(addressInput, address);
        saveProfileButton.click();
    }

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(changePasswordButton)).click();
        currentPasswordInput.sendKeys(currentPassword);
        newPasswordInput.sendKeys(newPassword);
        confirmPasswordInput.sendKeys(confirmPassword);
        savePasswordButton.click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).getText();
    }

    public void navigateToElectrodomesticos() {
        wait.until(ExpectedConditions.elementToBeClickable(electrodomesticosSection)).click();
    }

    public boolean isAddApplianceButtonVisible() {
        return wait.until(ExpectedConditions.visibilityOf(addApplianceButton)).isDisplayed();
    }

    private void clearAndType(WebElement element, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOf(element));
        el.clear();
        el.sendKeys(text);
    }
}