package co.com.electromovil.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ServiciosPage extends BasePage {
    @FindBy(css = "button[data-testid='nuevo-servicio']")
    private WebElement nuevoServicioButton;

    @FindBy(name = "tipo_equipo")
    private WebElement tipoEquipoSelect;

    @FindBy(name = "marca")
    private WebElement marcaInput;

    @FindBy(name = "modelo")
    private WebElement modeloInput;

    @FindBy(name = "descripcion_problema")
    private WebElement descripcionInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = ".servicios-list .servicio-item")
    private List<WebElement> serviciosList;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    @FindBy(css = ".confirmation-modal button.confirm")
    private WebElement confirmButton;

    public ServiciosPage(WebDriver driver) {
        super(driver);
    }

    public void clickNuevoServicio() {
        wait.until(ExpectedConditions.elementToBeClickable(nuevoServicioButton)).click();
    }

    public void fillServicioForm(String tipoEquipo, String marca, String modelo, String descripcion) {
        Select tipoSelect = new Select(wait.until(ExpectedConditions.visibilityOf(tipoEquipoSelect)));
        tipoSelect.selectByValue(tipoEquipo);
        
        marcaInput.sendKeys(marca);
        modeloInput.sendKeys(modelo);
        descripcionInput.sendKeys(descripcion);
    }

    public void submitServicio() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOf(successMessage)).getText();
    }

    public List<WebElement> getServiciosList() {
        return wait.until(ExpectedConditions.visibilityOfAllElements(serviciosList));
    }

    public void cancelarServicio(String servicioId) {
        WebElement cancelButton = driver.findElement(By.cssSelector(
            String.format("[data-servicio-id='%s'] button.cancelar", servicioId)));
        cancelButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    public String getServicioStatus(String servicioId) {
        WebElement statusElement = driver.findElement(By.cssSelector(
            String.format("[data-servicio-id='%s'] .status", servicioId)));
        return statusElement.getText();
    }
}