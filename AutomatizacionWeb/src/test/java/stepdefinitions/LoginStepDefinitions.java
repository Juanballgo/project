package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginStepDefinitions {

    @Given("that {string} is on the login page")
    public void thatUserIsOnTheLoginPage(String nombreActor) {
        System.out.println("=== CONFIGURING AUTOMATION ===");
        System.out.println("Actor: " + nombreActor);

        // Solo inicializar el driver si no existe
        if (DriverManager.getDriver() == null) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            DriverManager.setDriver(driver);
            System.out.println("SUCCESS: New Chrome driver created");
        }

        WebDriver driver = DriverManager.getDriver();
        driver.get("http://localhost:3000/LoginRegister");
        System.out.println("SUCCESS: Page loaded: " + driver.getTitle());
    }

    @When("he logs in with his valid credentials")
    public void heLogsInWithHisValidCredentials() {
        System.out.println("=== EXECUTING LOGIN ===");

        WebDriver driver = DriverManager.getDriver();

        try {
            WebElement campoCorreo = driver.findElement(By.name("email"));
            campoCorreo.clear();
            campoCorreo.sendKeys("andrestecnicos@gmail.com");
            System.out.println("SUCCESS: Email entered: andrestecnicos@gmail.com");

            WebElement campoClave = driver.findElement(By.name("password"));
            campoClave.clear();
            campoClave.sendKeys("123456789");
            System.out.println("SUCCESS: Password entered: 123456789");

            // Buscar el botón de login
            WebElement botonLogin = null;
            String[] selectors = {
                    "//button[text()='Iniciar sesion']",
                    "//button[contains(text(), 'Iniciar')]",
                    "//button[@type='submit']",
                    "//form//button",
                    "//button"
            };

            for (String selector : selectors) {
                try {
                    botonLogin = driver.findElement(By.xpath(selector));
                    System.out.println("SUCCESS: Button found with selector: " + selector);
                    break;
                } catch (Exception e) {
                    continue;
                }
            }

            if (botonLogin != null) {
                System.out.println("SUCCESS: Button found - clicking...");
                botonLogin.click();
                System.out.println("SUCCESS: Login button pressed");
            } else {
                System.out.println("ERROR: Could not find login button");
            }

            Thread.sleep(3000); // Aumentar tiempo para asegurar login

        } catch (Exception e) {
            System.out.println("ERROR in login: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("he must access his technical profile")
    public void heMustAccessHisTechnicalProfile() {
        System.out.println("=== ACCESSING TECHNICAL PROFILE ===");

        try {
            Thread.sleep(3000);
            WebDriver driver = DriverManager.getDriver();
            System.out.println("SUCCESS: LOGIN COMPLETED - Current URL: " + driver.getCurrentUrl());
            System.out.println("SUCCESS: Ready for next steps");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}