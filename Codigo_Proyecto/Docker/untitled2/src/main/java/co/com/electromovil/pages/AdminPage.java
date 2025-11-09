package co.com.electromovil.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class AdminPage extends BasePage {
    @FindBy(css = "input[data-testid='user-search']")
    private WebElement userSearchInput;

    @FindBy(css = ".users-list .user-item")
    private List<WebElement> usersList;

    @FindBy(css = ".pending-services .service-item")
    private List<WebElement> pendingServicesList;
    
    public List<WebElement> getPendingServicesList() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(pendingServicesList));
    }

    @FindBy(name = "technician_id")
    private WebElement technicianSelect;

    @FindBy(name = "start_date")
    private WebElement startDateInput;

    @FindBy(name = "end_date")
    private WebElement endDateInput;

    @FindBy(css = "button[data-testid='generate-report']")
    private WebElement generateReportButton;

    @FindBy(css = "button[data-testid='export-report']")
    private WebElement exportReportButton;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void searchUser(String searchTerm) {
        wait.until(ExpectedConditions.visibilityOf(userSearchInput)).sendKeys(searchTerm);
    }

    public List<WebElement> getUsersList() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(usersList));
    }

    public void changeUserRole(String userId, String newRole) {
        WebElement roleSelect = driver.findElement(By.cssSelector(
            String.format("[data-user-id='%s'] select.role", userId)));
        new Select(roleSelect).selectByValue(newRole);
    }

    public void assignTechnicianToService(String serviceId, String technicianId) {
        WebElement service = driver.findElement(By.cssSelector(
            String.format("[data-service-id='%s']", serviceId)));
        service.click();
        
        Select select = new Select(wait.until(ExpectedConditions.visibilityOf(technicianSelect)));
        select.selectByValue(technicianId);
    }

    public void setReportDateRange(String startDate, String endDate) {
        startDateInput.sendKeys(startDate);
        endDateInput.sendKeys(endDate);
    }

    public void generateReport() {
        wait.until(ExpectedConditions.elementToBeClickable(generateReportButton)).click();
    }

    public void exportReport() {
        wait.until(ExpectedConditions.elementToBeClickable(exportReportButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).getText();
    }
}