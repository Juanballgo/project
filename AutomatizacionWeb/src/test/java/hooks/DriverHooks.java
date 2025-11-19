package hooks;

import io.cucumber.java.AfterAll;
import stepdefinitions.DriverManager;

/**
 * Hook to ensure the WebDriver is quit after all Cucumber scenarios finish.
 * Added as a separate hook class to avoid changing existing hooks.
 */
public class DriverHooks {

    @AfterAll
    public static void closeDriver() {
        System.out.println("=== CERRANDO WEBDRIVER GLOBAL ===");
        try {
            DriverManager.quitDriver();
        } catch (Throwable t) {
            System.err.println("Error cerrando WebDriver: " + t.getMessage());
        }
    }
}
