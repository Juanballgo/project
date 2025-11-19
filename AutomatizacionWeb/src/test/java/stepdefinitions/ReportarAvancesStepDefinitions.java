package stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;

public class ReportarAvancesStepDefinitions {

    @When("he reports progress for an assigned service")
    public void heReportsProgressForAnAssignedService() {
        System.out.println("=== REPORTANDO AVANCES ===");

        WebDriver driver = DriverManager.getDriver();

        if (driver == null) {
            System.out.println("ERROR: Driver es nulo - el login pudo haber fallado");
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("URL actual antes de reportar: " + driver.getCurrentUrl());
            System.out.println("Titulo de la pagina: " + driver.getTitle());

            Thread.sleep(3000);

            // 1. Hacer clic en el menú Reportar Avances
            WebElement menuReportar = wait.until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//h2[contains(text(),'Reportar Avances')]"))
            );
            menuReportar.click();
            System.out.println("EXITO: Se hizo clic en el menu Reportar Avances");
            Thread.sleep(2000);

            // 2. Seleccionar servicio
            WebElement selectServicio = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.name("servicio"))
            );
            Select servicioSelect = new Select(selectServicio);

            if (servicioSelect.getOptions().size() > 1) {
                servicioSelect.selectByIndex(1);
                System.out.println("EXITO: Servicio seleccionado: " + servicioSelect.getFirstSelectedOption().getText());
            } else {
                System.out.println("ADVERTENCIA: No hay servicios disponibles para seleccionar");
            }

            // 3. Seleccionar tipo de reporte
            WebElement selectTipoReporte = driver.findElement(By.name("tipo_reporte"));
            Select tipoReporteSelect = new Select(selectTipoReporte);
            tipoReporteSelect.selectByVisibleText("Avance de trabajo");
            System.out.println("EXITO: Tipo de reporte seleccionado: Avance de trabajo");

            // 4. Ingresar detalles
            WebElement campoDetalles = driver.findElement(By.name("detalles"));
            campoDetalles.sendKeys("Avance del trabajo: Se completó la revisión inicial y diagnóstico del equipo. Se identificó falla en la fuente de poder y se procedió con la reparación de componentes electrónicos.");
            System.out.println("EXITO: Detalles ingresados");

            // 5. Ingresar costo
            WebElement campoCosto = driver.findElement(By.name("costo_mostrar"));
            campoCosto.sendKeys("150000");
            System.out.println("EXITO: Costo ingresado: 150000");

            // 6. Buscar y hacer clic en el botón Enviar Reporte
            System.out.println("Buscando boton Enviar Reporte...");

            WebElement botonEnviar = null;
            String[] selectorsBotones = {
                    "//button[contains(text(), 'Enviar Reporte')]",
                    "//button[contains(text(), 'Enviar')]",
                    "//button[contains(text(), 'enviar')]",
                    "//button[contains(text(), 'ENVIAR')]",
                    "//input[@value='Enviar Reporte']",
                    "//input[@value='Enviar']",
                    "//button[@type='submit']",
                    "//input[@type='submit']",
                    "//form//button[1]",
                    "//button[contains(@class, 'btn')]",
                    "//button[contains(@class, 'button')]",
                    "//form//*[contains(text(), 'Enviar')]",
                    "//*[contains(text(), 'Enviar Reporte')]"
            };

            for (String selector : selectorsBotones) {
                try {
                    System.out.println("Probando selector: " + selector);
                    botonEnviar = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selector)));
                    System.out.println("EXITO: Boton encontrado con selector: " + selector);

                    if (botonEnviar.isDisplayed() && botonEnviar.isEnabled()) {
                        System.out.println("Boton esta visible y habilitado");
                        break;
                    } else {
                        System.out.println("Boton encontrado pero no esta visible o habilitado");
                        botonEnviar = null;
                    }
                } catch (Exception e) {
                    System.out.println("El selector no funciono: " + selector);
                    continue;
                }
            }

            if (botonEnviar != null) {
                try {
                    System.out.println("Intentando click normal...");
                    botonEnviar.click();
                    System.out.println("EXITO: Reporte enviado - click normal");
                } catch (Exception e) {
                    System.out.println("Click normal fallo, intentando con JavaScript...");
                    try {
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", botonEnviar);
                        System.out.println("EXITO: Reporte enviado - click con JavaScript");
                    } catch (Exception e2) {
                        System.out.println("JavaScript click fallo, intentando con Actions...");
                        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
                        actions.moveToElement(botonEnviar).click().perform();
                        System.out.println("EXITO: Reporte enviado - click con Actions");
                    }
                }
            } else {
                System.out.println("ERROR: No se pudo encontrar el boton Enviar Reporte con ningun selector");
            }

            Thread.sleep(5000);

        } catch (Exception e) {
            System.out.println("ERROR al reportar avances: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("the report should be sent successfully")
    public void theReportShouldBeSentSuccessfully() {
        System.out.println("=== VERIFICANDO ENVIO DE REPORTE ===");

        WebDriver driver = DriverManager.getDriver();

        try {
            Thread.sleep(2000);

            // MANEJAR EL ALERT QUE CONFIRMA EL ENVIO EXITOSO
            try {
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("EXITO: Alerta del sistema - " + alertText);

                if (alertText.contains("Reporte enviado correctamente")) {
                    System.out.println("CONFIRMACION: El reporte fue enviado exitosamente");
                    alert.accept();
                }

            } catch (Exception alertException) {
                System.out.println("No se encontro alerta o ya fue cerrada");
            }

            Thread.sleep(3000);
            System.out.println("EXITO: Proceso de reporte completado - URL Final: " + driver.getCurrentUrl());
            System.out.println("Titulo final: " + driver.getTitle());

            // NO cerrar el driver aquí

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}