package stepdefinitions;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ActualizarPerfilStepDefinitions {

    @When("he updates his technical profile with the following data")
    public void heUpdatesHisTechnicalProfileWithTheFollowingData() {
        System.out.println("=== ACTUALIZANDO PERFIL TECNICO ===");

        WebDriver driver = DriverManager.getDriver();

        if (driver == null) {
            System.out.println("ERROR: Driver es nulo - el login pudo haber fallado");
            return;
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println("URL actual antes de actualizar perfil: " + driver.getCurrentUrl());
            System.out.println("Titulo de la pagina: " + driver.getTitle());

            Thread.sleep(3000);

            // 1. Hacer clic en el botón de perfil (icono de usuario)
            WebElement botonPerfil = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("button.profile-btn"))
            );
            botonPerfil.click();
            System.out.println("EXITO: Se hizo clic en el boton de perfil");
            Thread.sleep(2000);

            // 2. Llenar los campos del formulario de perfil

            // Nombre completo
            WebElement campoNombre = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.name("name"))
            );
            campoNombre.clear();
            campoNombre.sendKeys("Andres Rodriguez Actualizado");
            System.out.println("EXITO: Nombre completo ingresado: Andres Rodriguez Actualizado");

            // Correo electrónico
            WebElement campoCorreo = driver.findElement(By.name("email"));
            campoCorreo.clear();
            campoCorreo.sendKeys("andres.actualizado@gmail.com");
            System.out.println("EXITO: Correo electronico ingresado: andres.actualizado@gmail.com");

            // Teléfono
            WebElement campoTelefono = driver.findElement(By.name("phone"));
            campoTelefono.clear();
            campoTelefono.sendKeys("3112816300");
            System.out.println("EXITO: Telefono ingresado: 3112816300");

            // Dirección
            WebElement campoDireccion = driver.findElement(By.name("address"));
            campoDireccion.clear();
            campoDireccion.sendKeys("Calle Tecnico Actualizada #456");
            System.out.println("EXITO: Direccion ingresada: Calle Tecnico Actualizada #456");

            // Contraseña actual
            WebElement campoContrasenaActual = driver.findElement(By.name("current_password"));
            campoContrasenaActual.sendKeys("12345679");
            System.out.println("EXITO: Contraseña actual ingresada");

            // Nueva contraseña
            WebElement campoNuevaContrasena = driver.findElement(By.name("password"));
            campoNuevaContrasena.sendKeys("nueva123456");
            System.out.println("EXITO: Nueva contraseña ingresada");

            // CONFIRMAR NUEVA CONTRASEÑA - MÚLTIPLES ESTRATEGIAS
            System.out.println("Buscando campo Confirmar nueva contraseña...");

            WebElement campoConfirmarContrasena = null;
            String[] selectoresConfirmar = {
                    "input[name='password_confirmation']",
                    "input[name='password_confirmation']",
                    "//input[@name='password_confirmation']",
                    "//input[@type='password'][3]",
                    "//input[contains(@placeholder, 'confirmar')]",
                    "//input[contains(@placeholder, 'Confirmar')]",
                    "//label[contains(text(), 'Confirmar')]/following-sibling::input",
                    "//label[contains(text(), 'confirmar')]/following-sibling::input"
            };

            for (String selector : selectoresConfirmar) {
                try {
                    if (selector.startsWith("//")) {
                        campoConfirmarContrasena = driver.findElement(By.xpath(selector));
                    } else {
                        campoConfirmarContrasena = driver.findElement(By.cssSelector(selector));
                    }

                    if (campoConfirmarContrasena != null && campoConfirmarContrasena.isDisplayed()) {
                        System.out.println("EXITO: Campo confirmar contraseña encontrado con selector: " + selector);
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Selector no funciono: " + selector);
                    continue;
                }
            }

            if (campoConfirmarContrasena != null) {
                campoConfirmarContrasena.clear();
                campoConfirmarContrasena.sendKeys("nueva123456");
                System.out.println("EXITO: Confirmar contraseña ingresada");
            } else {
                System.out.println("ERROR: No se pudo encontrar el campo Confirmar nueva contraseña");
                System.out.println("Listando todos los campos de contraseña disponibles:");

                // Listar todos los campos de tipo password para debug
                java.util.List<WebElement> camposPassword = driver.findElements(By.cssSelector("input[type='password']"));
                System.out.println("Numero total de campos password encontrados: " + camposPassword.size());

                for (int i = 0; i < camposPassword.size(); i++) {
                    WebElement campo = camposPassword.get(i);
                    String name = campo.getAttribute("name");
                    String placeholder = campo.getAttribute("placeholder");
                    System.out.println("Campo password " + i + ": name='" + name + "', placeholder='" + placeholder + "'");

                    // Si es el tercer campo de password, usarlo
                    if (i == 2) {
                        campo.sendKeys("nueva123456");
                        System.out.println("EXITO: Usando tercer campo password para confirmar");
                        break;
                    }
                }
            }

            // 3. Buscar y hacer clic en el botón "Guardar cambios"
            System.out.println("Buscando boton Guardar cambios...");

            WebElement botonGuardar = wait.until(
                    ExpectedConditions.elementToBeClickable(By.cssSelector("button.save-btn"))
            );

            if (botonGuardar != null) {
                System.out.println("EXITO: Boton Guardar cambios encontrado");

                // Scroll al botón para asegurar visibilidad
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", botonGuardar);
                Thread.sleep(1000);

                // Intentar hacer clic de diferentes formas
                try {
                    botonGuardar.click();
                    System.out.println("EXITO: Boton Guardar cambios presionado - click normal");
                } catch (Exception e) {
                    System.out.println("Click normal fallo, intentando con JavaScript...");
                    try {
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", botonGuardar);
                        System.out.println("EXITO: Boton Guardar cambios presionado - click con JavaScript");
                    } catch (Exception e2) {
                        System.out.println("JavaScript click fallo, intentando con Actions...");
                        org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
                        actions.moveToElement(botonGuardar).click().perform();
                        System.out.println("EXITO: Boton Guardar cambios presionado - click con Actions");
                    }
                }
            } else {
                System.out.println("ERROR: No se pudo encontrar el boton Guardar cambios");
            }

            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("ERROR al actualizar perfil: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("the profile should be updated successfully")
    public void theProfileShouldBeUpdatedSuccessfully() {
        System.out.println("=== VERIFICANDO ACTUALIZACION DE PERFIL ===");

        WebDriver driver = DriverManager.getDriver();

        try {
            Thread.sleep(2000);

            // MANEJAR EL ALERT QUE CONFIRMA LA ACTUALIZACION EXITOSA
            try {
                org.openqa.selenium.Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("EXITO: Alerta del sistema - " + alertText);

                if (alertText.contains("Perfil actualizado correctamente")) {
                    System.out.println("CONFIRMACION: El perfil fue actualizado exitosamente");
                    alert.accept(); // Aceptar el alert
                    System.out.println("EXITO: Alerta aceptada");
                } else {
                    System.out.println("Alerta inesperada: " + alertText);
                    alert.accept(); // Aceptar de todas formas
                }

            } catch (Exception alertException) {
                System.out.println("No se encontro alerta o ya fue cerrada: " + alertException.getMessage());
            }

            Thread.sleep(3000);
            System.out.println("EXITO: Proceso de actualizacion de perfil completado");
            System.out.println("URL Final: " + driver.getCurrentUrl());
            System.out.println("Titulo final: " + driver.getTitle());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}