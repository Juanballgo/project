
package co.com.electromovil.tasks;

import co.com.electromovil.userinterface.UsuarioPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.screenplay.actions.Clear;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AgregarElectrodomestico implements Task {
    private final String tipo;
    private final String marca;
    private final String modelo;
    private final String fechaCompra;

    public AgregarElectrodomestico(String tipo, String marca, String modelo, String fechaCompra) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.fechaCompra = fechaCompra;
    }

    public static AgregarElectrodomestico conDatos(String tipo, String marca, String modelo, String fechaCompra) {
        return new AgregarElectrodomestico(tipo, marca, modelo, fechaCompra);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(UsuarioPage.ADD_APPLIANCE_BTN, isVisible()).forNoMoreThan(5).seconds(),
            WaitUntil.the(UsuarioPage.ADD_APPLIANCE_BTN, isEnabled()).forNoMoreThan(5).seconds(),
            // Scroll a la parte superior para evitar overlays fijos
            Task.where("Scroll top", actor1 -> {
                WebDriver driver = BrowseTheWeb.as(actor1).getDriver();
                try { driver.switchTo().defaultContent(); driver.findElement(org.openqa.selenium.By.tagName("body")).sendKeys(org.openqa.selenium.Keys.HOME); } catch (Exception e) {
                    try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollTo(0,0);"); } catch (Exception ignored) {}
                }
            }),
            // Esperar a que no haya overlays visibles
            Task.where("Esperar overlays", actor1 -> {
                WebDriver driver = BrowseTheWeb.as(actor1).getDriver();
                long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < 5000) {
                    boolean overlays = !driver.findElements(org.openqa.selenium.By.cssSelector(".modal, .backdrop, .toast, .overlay, .MuiBackdrop-root, .ant-modal-mask")).isEmpty();
                    if (!overlays) break;
                    try { Thread.sleep(200); } catch (Exception ignored) {}
                }
            }),
            // Scroll al botón justo antes del click
            Scroll.to(UsuarioPage.ADD_APPLIANCE_BTN),
            // Captura de pantalla antes del click para depuración
            Task.where("Captura de pantalla antes del click", actor1 -> {
                WebDriver driver = BrowseTheWeb.as(actor1).getDriver();
                try {
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    java.nio.file.Files.write(java.nio.file.Paths.get("appliance_click_blocked.png"), screenshot);
                } catch (Exception ignored) {}
            }),
            // Click solo por JavaScript
            Task.where("Click JS forzado", actor1 -> {
                JavaScriptClick.on(UsuarioPage.ADD_APPLIANCE_BTN).performAs(actor1);
            }),
            WaitUntil.the(UsuarioPage.APPLIANCE_FORM, isVisible()).forNoMoreThan(10).seconds(),
            Scroll.to(UsuarioPage.APPLIANCE_FORM),
            WaitUntil.the(UsuarioPage.APPLIANCE_TYPE, isVisible()).forNoMoreThan(5).seconds(),
            SelectFromOptions.byValue(tipo).from(UsuarioPage.APPLIANCE_TYPE),
            Clear.field(UsuarioPage.APPLIANCE_BRAND),
            Enter.theValue(marca).into(UsuarioPage.APPLIANCE_BRAND),
            Clear.field(UsuarioPage.APPLIANCE_MODEL),
            Enter.theValue(modelo).into(UsuarioPage.APPLIANCE_MODEL),
            Clear.field(UsuarioPage.APPLIANCE_PURCHASE_DATE),
            Enter.theValue(fechaCompra).into(UsuarioPage.APPLIANCE_PURCHASE_DATE),
            WaitUntil.the(UsuarioPage.SAVE_APPLIANCE_BTN, isVisible()).forNoMoreThan(10).seconds(),
            WaitUntil.the(UsuarioPage.SAVE_APPLIANCE_BTN, isEnabled()).forNoMoreThan(5).seconds(),
            // Scroll preciso al botón con JavaScript
            Task.where("Scroll JS al botón guardar electrodoméstico", actor1 -> {
                WebDriver driver = BrowseTheWeb.as(actor1).getDriver();
                try {
                    org.openqa.selenium.WebElement btn = driver.findElement(org.openqa.selenium.By.cssSelector(".appliance-form .save-btn"));
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
                } catch (Exception ignored) {}
            }),
            // Click JS robusto
            Task.where("Click JS guardar electrodoméstico", actor1 -> {
                JavaScriptClick.on(UsuarioPage.SAVE_APPLIANCE_BTN).performAs(actor1);
            })
        );
    }
}
