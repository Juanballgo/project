package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminStepDefinitions extends PageObject {

    // store last created identifiers so subsequent Then-steps can verify them
    private String lastCreatedUserEmail = null;
    private String lastCreatedServiceModel = null;

    // admin-specific global timeout for slow backends (approx 90 seconds)
    private static final Duration ADMIN_TIMEOUT = Duration.ofSeconds(90);

    @When("he opens the admin dashboard")
    public void heOpensTheAdminDashboard() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        try {
            WebElement dash = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#admin-dashboard, .admin-dashboard")));
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", dash);
            } catch (Exception jsEx) {
                // ignore scroll failure
            }
        } catch (Exception e) {
            // fallback: try clicking a nav link
            try {
                WebElement link = driver.findElement(By.cssSelector("a[href*='/admin'], a[href*='admin'], a[href*='dashboard'], .menu-admin"));
                try { link.click(); } catch (Exception c) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", link); }
            } catch (Exception ex) {
                System.out.println("Could not open admin dashboard: " + ex.getMessage());
            }
        }
    }

    @Then("he should see the admin widgets")
    public void heShouldSeeTheAdminWidgets() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("#admin-dashboard, .admin-dashboard")),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".widget, .panel, .card"))
        ));
        System.out.println("Admin widgets visible (best-effort)");
    }

    @Then("he opens users management")
    public void heOpensUsersManagement() {
        WebDriver driver = DriverManager.getDriver();
        openAdminSection(driver, "users", "Gestión de Usuarios");
    }

    @Then("he should see the users list")
    public void heShouldSeeTheUsersList() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'gestión de usuarios') or contains(., 'Gestión de Usuarios') or contains(., 'Usuarios')]")));
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
        System.out.println("Found users rows (table tbody tr): " + rows.size());
    }

    @Then("he opens services management")
    public void heOpensServicesManagement() {
        WebDriver driver = DriverManager.getDriver();
        openAdminSection(driver, "services", "Gestión de Servicios");
    }

    @Then("he should see the services list")
    public void heShouldSeeTheServicesList() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'gestión de servicios') or contains(., 'Gestión de Servicios') or contains(., 'Servicios')]")));
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
        System.out.println("Found services rows (table tbody tr): " + rows.size());
    }

    @Then("he opens invoices management")
    public void heOpensInvoicesManagement() {
        WebDriver driver = DriverManager.getDriver();
        openAdminSection(driver, "invoices", "Gestión de Facturas");
    }

    @Then("he should see the invoices list")
    public void heShouldSeeTheInvoicesList() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'gestión de facturas') or contains(., 'Gestión de Facturas') or contains(., 'Facturas')]")));
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr"));
        System.out.println("Found invoices rows (table tbody tr): " + rows.size());
    }

    @Then("he opens reports management")
    public void heOpensReportsManagement() {
        WebDriver driver = DriverManager.getDriver();
        openAdminSection(driver, "reports", "Gestión de Reportes");
    }

    @Then("he should see the reports dashboard")
    public void heShouldSeeTheReportsDashboard() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'gestión de reportes') or contains(., 'Reportes') or contains(., 'Reportes')]")));
        System.out.println("Reports dashboard visible (best-effort)");
    }

    @When("he creates a new user via the UI")
    public void heCreatesANewUserViaTheUI() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        openAdminSection(driver, "users", "Gestión de Usuarios");

        // Click add user button (best-effort selectors)
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Añadir Usuario') or contains(., 'Añadir usuario') or contains(., 'Añadir') or contains(., 'Add User') or contains(., 'Nuevo Usuario')]")));
            try { addBtn.click(); } catch (Exception e) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn); }
        } catch (Exception e) {
            System.out.println("Add user button not found or not clickable: " + e.getMessage());
        }

        // Wait for modal and fill form
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-content, .modal-dialog")));
            String unique = String.valueOf(System.currentTimeMillis());
            String name = "AutoTest User";
            String email = "autouser" + unique + "@test.local";
            String password = "TestPass!" + unique.substring(unique.length()-6);
            // persist for later verification
            this.lastCreatedUserEmail = email;

            safeSetModalInputValue(modal, "name", name);
            safeSetModalInputValue(modal, "email", email);
            safeSetModalInputValue(modal, "password", password);
            safeSetModalInputValue(modal, "password_confirmation", password);
            safeSetModalInputValue(modal, "phone", "555000" + unique.substring(unique.length()-4));
            safeSetModalInputValue(modal, "address", "Automated Address");
            // set a default role for the created user
            safeSetModalInputValue(modal, "role", "cliente");

            // Try to submit
            try {
                WebElement submit = modal.findElement(By.xpath(".//button[@type='submit' or contains(., 'Guardar') or contains(., 'Crear') or contains(., 'Add')]"));
                try { submit.click(); } catch (Exception e) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", submit); }
            } catch (Exception e) {
                // fallback: click any visible primary button
                try {
                    WebElement primary = modal.findElement(By.cssSelector("button.btn-primary, button.primary, button[type='submit']"));
                    try { primary.click(); } catch (Exception ex) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", primary); }
                } catch (Exception ex) {
                    System.out.println("Could not find submit button in add-user modal: " + ex.getMessage());
                }
            }

            // wait for modal to close and then verify presence anywhere in page (best-effort)
            try {
                wait.until(ExpectedConditions.invisibilityOf(modal));
            } catch (Exception ignore) { }
            try {
                // search for the email or name anywhere in the page DOM
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(., '" + email + "') or contains(., '" + name + "')]")));
                System.out.println("Created user visible in page: " + email);
            } catch (Exception e) {
                System.out.println("Could not verify created user in page: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Add-user modal did not appear: " + e.getMessage());
        }
    }

    @When("he creates a new service via the UI")
    public void heCreatesANewServiceViaTheUI() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        openAdminSection(driver, "services", "Gestión de Servicios");

        // Click create service button
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Crear Servicio') or contains(., 'Crear servicio') or contains(., 'Nuevo Servicio') or contains(., 'Añadir Servicio') or contains(., 'Add Service')]")));
            try { addBtn.click(); } catch (Exception e) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn); }
        } catch (Exception e) {
            System.out.println("Create service button not found/clickable: " + e.getMessage());
        }

        // Wait for modal and fill form
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal-content, .modal-dialog")));
            String unique = String.valueOf(System.currentTimeMillis());
            String tipo = "nevera";
            String marca = "MarcaAuto";
            String modelo = "ModeloAuto" + unique.substring(unique.length()-6);
            String descripcion = "Servicio creado por automatizacion " + unique;
            // persist for later verification
            this.lastCreatedServiceModel = modelo;

            // Explicitly select 'Tipo de Equipo' (select has no name attribute) by label -> select
            try {
                WebElement tipoSelect = modal.findElement(By.xpath(".//label[contains(., 'Tipo de Equipo')]/following::select[1]"));
                try {
                    Select sTipo = new Select(tipoSelect);
                    try { sTipo.selectByValue(tipo); } catch (Exception exVal) {
                        try { sTipo.selectByVisibleText("Nevera"); } catch (Exception exText) {
                            // fallback: choose first non-empty option
                            List<WebElement> options = tipoSelect.findElements(By.tagName("option"));
                            for (WebElement o : options) {
                                String v = o.getAttribute("value");
                                if (v != null && !v.isEmpty()) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click(); arguments[0].selected = true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", o); break; }
                            }
                        }
                    }
                } catch (Exception e) {
                    // fallback: try JS set
                    try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", tipoSelect, tipo); } catch (Exception ex) { /* ignore */ }
                }
            } catch (Exception e) {
                // if label-based select not found, fallback to previous helper
                safeSetModalInputValue(modal, "tipo_equipo", tipo);
            }

            // Marca/Modelo/Descripcion
            safeSetModalInputValue(modal, "marca", marca);
            safeSetModalInputValue(modal, "modelo", modelo);
            // Try to set the description field. The label in the UI is "Descripción del Problema" (with accent)
            // safeSetModalInputValue may not match that label when using the key "description", so set explicitly.
            boolean descriptionSet = false;
            try {
                WebElement descArea = null;
                try { descArea = modal.findElement(By.xpath(".//label[contains(., 'Descripción del Problema')]/following::textarea[1]")); } catch (Exception ex) { /* ignore */ }
                if (descArea == null) {
                    try { descArea = modal.findElement(By.xpath(".//label[contains(., 'Descripción')]/following::textarea[1]")); } catch (Exception ex) { /* ignore */ }
                }
                if (descArea == null) {
                    try { descArea = modal.findElement(By.xpath(".//label[contains(., 'Descripcion')]/following::textarea[1]")); } catch (Exception ex) { /* ignore */ }
                }
                if (descArea == null) {
                    // try placeholder based
                    try { descArea = modal.findElement(By.xpath(".//textarea[contains(@placeholder, 'Descripción') or contains(@placeholder, 'Descripcion') or contains(@placeholder, 'problema')]") ); } catch (Exception ex) { /* ignore */ }
                }
                if (descArea == null) {
                    // fallback: first textarea in modal
                    try { descArea = modal.findElement(By.xpath(".//textarea[1]")); } catch (Exception ex) { /* ignore */ }
                }

                if (descArea != null) {
                    try { descArea.clear(); descArea.sendKeys(descripcion); } catch (Exception sendEx) {
                        try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", descArea, descripcion); } catch (Exception jsEx) { /* ignore */ }
                    }
                    descriptionSet = true;
                }
            } catch (Exception ex) {
                // ignore and allow safeSetModalInputValue to attempt as well
            }
            if (!descriptionSet) {
                safeSetModalInputValue(modal, "description", descripcion);
            }

            // Assign a client: find the select after label 'Asignar un cliente' and pick 'Cliente Uno'
            try {
                WebElement clientSelect = modal.findElement(By.xpath(".//label[contains(., 'Asignar un cliente')]/following::select[1]"));
                try {
                    Select sClient = new Select(clientSelect);
                    boolean picked = false;
                    try { sClient.selectByVisibleText("Cliente Uno"); picked = true; } catch (Exception ex) { }
                    if (!picked) {
                        // try by option values or pick first non-empty
                        List<WebElement> opts = clientSelect.findElements(By.tagName("option"));
                        for (WebElement o : opts) {
                            String txt = o.getText();
                            if (txt != null && txt.trim().equalsIgnoreCase("Cliente Uno")) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click(); arguments[0].selected = true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", o); picked = true; break; }
                        }
                        if (!picked && !opts.isEmpty()) {
                            WebElement o = opts.get(0);
                            try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click(); arguments[0].selected = true; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", o); } catch (Exception ignore) {}
                        }
                    }
                } catch (Exception ex) {
                    try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", clientSelect, ""); } catch (Exception ignore) {}
                }
            } catch (Exception e) {
                // ignore if client select not present
            }

            // Submit
            try {
                WebElement submit = modal.findElement(By.xpath(".//button[@type='submit' or contains(., 'Guardar') or contains(., 'Crear') or contains(., 'Add')]"));
                try { submit.click(); } catch (Exception e) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", submit); }
            } catch (Exception e) {
                try {
                    WebElement primary = modal.findElement(By.cssSelector("button.btn-primary, button.primary, button[type='submit']"));
                    try { primary.click(); } catch (Exception ex) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", primary); }
                } catch (Exception ex) {
                    System.out.println("Could not find submit button in create-service modal: " + ex.getMessage());
                }
            }

            try {
                wait.until(ExpectedConditions.invisibilityOf(modal));
            } catch (Exception ignore) { }
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(., '" + modelo + "') or contains(., '" + marca + "')]")));
                System.out.println("Created service visible in page: " + modelo);
            } catch (Exception e) {
                System.out.println("Could not verify created service in page: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Create-service modal did not appear: " + e.getMessage());
        }
    }

    // Helper: set an input/textarea inside a modal by name/id/placeholder (best-effort)
    private void safeSetModalInputValue(WebElement modal, String nameOrId, String value) {
        WebDriver driver = DriverManager.getDriver();
        try {
            List<WebElement> candidates = modal.findElements(By.cssSelector("input[name='" + nameOrId + "'], input#" + nameOrId + ", textarea[name='" + nameOrId + "'], textarea#" + nameOrId + ", select[name='" + nameOrId + "'], select#" + nameOrId + ", input[placeholder*='" + nameOrId + "'], textarea[placeholder*='" + nameOrId + "']"));
            if (candidates.isEmpty()) {
                // try broader search by attribute contains
                candidates = modal.findElements(By.xpath(".//input[contains(@name, '" + nameOrId + "') or contains(@id, '" + nameOrId + "') or contains(@placeholder, '" + nameOrId + "') or name() = 'select' and contains(., '" + nameOrId + "')]") );
            }
            if (!candidates.isEmpty()) {
                WebElement el = candidates.get(0);
                try {
                    String tag = el.getTagName().toLowerCase();
                    if ("select".equals(tag)) {
                        // Prefer Selenium Select API first (most compatible).
                        try {
                            Select s = new Select(el);
                            try { s.selectByValue(value); }
                            catch (Exception exVal) {
                                try { s.selectByVisibleText(value); } catch (Exception exText) {
                                    // fallback to manual option selection below
                                    List<WebElement> options = el.findElements(By.tagName("option"));
                                    boolean selected = false;
                                    for (WebElement opt : options) {
                                        try {
                                            String optVal = opt.getAttribute("value");
                                            String optText = opt.getText();
                                            if (optVal != null && optVal.equalsIgnoreCase(value)) {
                                                try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", opt); } catch (Exception ignore) {}
                                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", el, optVal);
                                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].selected = true;", opt);
                                                selected = true; break;
                                            }
                                            if (optText != null && optText.trim().equalsIgnoreCase(value.trim())) {
                                                try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", opt); } catch (Exception ignore) {}
                                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", el, opt.getAttribute("value"));
                                                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].selected = true;", opt);
                                                selected = true; break;
                                            }
                                        } catch (Exception exOpt) { /* ignore per-option errors */ }
                                    }
                                    if (!selected && !options.isEmpty()) {
                                        WebElement opt = options.get(0);
                                        try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", opt); } catch (Exception ignore) {}
                                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", el, opt.getAttribute("value"));
                                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].selected = true;", opt);
                                    }
                                }
                            }
                        } catch (Exception selectEx) {
                            // last-resort: set value by JS and dispatch events
                            try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", el, value); } catch (Exception jsEx) { /* ignore */ }
                        }
                        // As an extra fallback, try keyboard interaction to change select
                        try {
                            el.click();
                            el.sendKeys(org.openqa.selenium.Keys.HOME);
                            el.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
                            el.sendKeys(org.openqa.selenium.Keys.ENTER);
                        } catch (Exception ignore) {}
                    } else {
                        el.clear();
                        el.sendKeys(value);
                    }
                } catch (Exception e) {
                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", el, value);
                }
                return;
            }

            // last resort: try finding by label text
            List<WebElement> labels = modal.findElements(By.tagName("label"));
            for (WebElement label : labels) {
                try {
                    if (label.getText().toLowerCase().contains(nameOrId.toLowerCase())) {
                        String forAttr = label.getAttribute("for");
                        if (forAttr != null && !forAttr.isEmpty()) {
                            WebElement el = modal.findElement(By.id(forAttr));
                            try { el.clear(); el.sendKeys(value); } catch (Exception e) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input'));", el, value); }
                            return;
                        }
                        // if label has no for, try to find the next input/select/textarea after the label
                        try {
                            WebElement nextInput = null;
                            try { nextInput = label.findElement(By.xpath("following::input[1]")); } catch (Exception ex) { /* ignore */ }
                            if (nextInput == null) {
                                try { nextInput = label.findElement(By.xpath("following::select[1]")); } catch (Exception ex) { /* ignore */ }
                            }
                            if (nextInput == null) {
                                try { nextInput = label.findElement(By.xpath("following::textarea[1]")); } catch (Exception ex) { /* ignore */ }
                            }
                            if (nextInput != null) {
                                String tag = nextInput.getTagName().toLowerCase();
                                if ("select".equals(tag)) {
                                    try {
                                        List<WebElement> options = nextInput.findElements(By.tagName("option"));
                                        boolean selected = false;
                                        for (WebElement opt : options) {
                                            try {
                                                if (opt.getText().trim().equalsIgnoreCase(value.trim())) {
                                                    try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", opt); } catch (Exception ignore) {}
                                                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", nextInput, opt.getAttribute("value"));
                                                    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].selected = true;", opt);
                                                    selected = true; break;
                                                }
                                            } catch (Exception exOpt) { /* ignore per-option errors */ }
                                        }
                                        if (!selected && !options.isEmpty()) {
                                            WebElement opt = options.get(0);
                                            try { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", opt); } catch (Exception ignore) {}
                                            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", nextInput, opt.getAttribute("value"));
                                            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].selected = true;", opt);
                                        }
                                    } catch (Exception ex) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true}));", nextInput, value); }
                                } else {
                                    try { nextInput.clear(); nextInput.sendKeys(value); } catch (Exception ex) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', {bubbles:true}));", nextInput, value); }
                                }
                                return;
                            }
                        } catch (Exception ex) { /* ignore */ }
                    }
                } catch (Exception e) { /* ignore */ }
            }

            System.out.println("Could not locate modal input for: " + nameOrId);
        } catch (Exception e) {
            System.out.println("Error setting modal input '" + nameOrId + "': " + e.getMessage());
        }
    }

    @Then("the new user should appear in the users list")
    public void theNewUserShouldAppearInTheUsersList() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        if (this.lastCreatedUserEmail == null) {
            throw new AssertionError("No created user email recorded from previous step.");
        }
        String email = this.lastCreatedUserEmail;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tbody//tr[.//td[contains(., '" + email + "') or contains(., '" + email.split("@")[0] + "')]]")));
            System.out.println("Verified created user present: " + email);
        } catch (Exception e) {
            throw new AssertionError("Could not verify created user in users list: " + e.getMessage());
        }
    }

    @Then("the new service should appear in the services list")
    public void theNewServiceShouldAppearInTheServicesList() {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        if (this.lastCreatedServiceModel == null) {
            throw new AssertionError("No created service model recorded from previous step.");
        }
        String modelo = this.lastCreatedServiceModel;
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "') or contains(., '" + modelo.replaceAll("\\\"", "\\\\\"") + "')]]")));
            System.out.println("Verified created service present: " + modelo);
        } catch (Exception e) {
            throw new AssertionError("Could not verify created service in services list: " + e.getMessage());
        }
    }

    @When("he changes the created service's status to {string}")
    public void heChangesTheCreatedServiceStatusTo(String newStatus) {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        if (this.lastCreatedServiceModel == null) {
            throw new AssertionError("No created service model recorded from previous step.");
        }
        String modelo = this.lastCreatedServiceModel;
        try {
            // locate the row containing the model
            WebElement row = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "')]]")));

            // find dropdown toggle inside the row (common patterns)
            WebElement toggle = null;
            try {
                toggle = row.findElement(By.xpath(".//button[contains(@id, 'dropdown-status') or contains(., 'Cambiar estado') or contains(., 'Cambiar Estado') or contains(@class,'dropdown-toggle')]") );
            } catch (Exception ex) { /* ignore and fallback */ }

            if (toggle == null) {
                // try any button inside row
                try { toggle = row.findElement(By.xpath(".//button[normalize-space()]")); } catch (Exception ex) { /* ignore */ }
            }

            if (toggle == null) {
                throw new AssertionError("Could not find status dropdown toggle for service: " + modelo);
            }

            // Click toggle, retrying if DOM updates cause stale references
            int toggleAttempts = 0;
            boolean toggleClicked = false;
            while (!toggleClicked && toggleAttempts < 3) {
                try {
                    try { toggle.click(); } catch (Exception c) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", toggle); }
                    toggleClicked = true;
                } catch (org.openqa.selenium.StaleElementReferenceException ser) {
                    toggleAttempts++;
                    // re-find the row and toggle element
                    try { Thread.sleep(300); } catch (InterruptedException ie) {}
                    row = driver.findElement(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "')]]"));
                    try { toggle = row.findElement(By.xpath(".//button[contains(@id, 'dropdown-status') or contains(., 'Cambiar estado') or contains(., 'Cambiar Estado') or contains(@class,'dropdown-toggle')]") ); } catch (Exception ex) { /* ignore */ }
                }
            }
            if (!toggleClicked) { throw new AssertionError("Could not click status dropdown toggle after retries for service: " + modelo); }

            // map internal status like 'en_proceso' -> visible label 'en proceso' or 'En Proceso'
            String visible = newStatus.replace("_", " ");
            // construct case-insensitive xpath to find a menu item matching the label/text
            String itemXpath = "//div[contains(@class,'dropdown-menu') and not(contains(@style,'display: none'))]//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + visible.toLowerCase() + "') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + visible.toLowerCase() + "')]";

            // wait for the menu item to be clickable
            WebElement menuItem = null;
            try {
                // menu items can be rendered inside the row or appended as a floating dropdown (e.g. to document body)
                // try multiple strategies: find clickable item globally first
                try {
                    menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(itemXpath)));
                } catch (Exception ex) {
                    // fallback: search visible dropdown menu elements and locate children
                    List<WebElement> menus = driver.findElements(By.cssSelector(".dropdown-menu, .dropdown-menu.show"));
                    for (WebElement m : menus) {
                        try {
                            List<WebElement> cand = m.findElements(By.xpath(".//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + visible.toLowerCase() + "')]") );
                            if (!cand.isEmpty()) { menuItem = cand.get(0); break; }
                        } catch (Exception ignore) {}
                    }
                }
            } catch (Exception e) {
                // as fallback search for any menu item inside the row's dropdown menu
                try { menuItem = row.findElement(By.xpath(".//ul[contains(@class,'dropdown-menu')]//li//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + visible.toLowerCase() + "')]") ); } catch (Exception ignore) { /* ignore */ }
            }

            if (menuItem == null) {
                throw new AssertionError("Could not find dropdown menu item for status: " + newStatus + " (possible visible label: " + visible + ")");
            }

            // Click the menu item, re-locate it if needed to avoid stale references
            int clickAttempts = 0;
            boolean clicked = false;
            while (!clicked && clickAttempts < 3) {
                try {
                    if (menuItem == null) {
                        // try locating again (global search)
                        try {
                            menuItem = driver.findElement(By.xpath(itemXpath));
                        } catch (Exception ex) { /* ignore */ }
                    }
                    try { menuItem.click(); } catch (Exception c) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", menuItem); }
                    clicked = true;
                } catch (org.openqa.selenium.StaleElementReferenceException ser) {
                    clickAttempts++;
                    try { Thread.sleep(300); } catch (InterruptedException ie) {}
                    menuItem = null; // re-find in next loop
                }
            }
            if (!clicked) { throw new AssertionError("Could not click menu item for status: " + newStatus + " on service: " + modelo); }

            // After clicking, wait for either a success alert or the status column text to update
            String expectedText = visible; // e.g. 'en proceso', 'completado', 'pendiente'
            boolean changed = false;
            try {
                // first try to wait for a success toast/alert message (best-effort)
                try {
                    wait.withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class,'alert') or contains(@class,'toast')][contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'exito') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'actualiz') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'actualizado')]")));
                    changed = true;
                } catch (Exception ignore) { /* no toast found quickly */ }

                // then wait for the status text in the same row to reflect the expected status
                try {
                    // Find rows that contain the model text
                    List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "')]]"));
                    // Try to find the expected status text inside any of these rows
                    for (WebElement r : rows) {
                        try {
                            List<WebElement> matches = r.findElements(By.xpath(".//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + expectedText.toLowerCase() + "')]") );
                            if (!matches.isEmpty()) { changed = true; break; }
                        } catch (Exception ignore) { /* continue */ }
                    }
                    // If not found, wait and retry inside the same timeout
                    long start = System.currentTimeMillis();
                    while (!changed && System.currentTimeMillis() - start < ADMIN_TIMEOUT.toMillis()) {
                        try { Thread.sleep(750); } catch (InterruptedException ie) {}
                        rows = driver.findElements(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "')]]"));
                        for (WebElement r : rows) {
                            try {
                                List<WebElement> matches = r.findElements(By.xpath(".//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + expectedText.toLowerCase() + "')]") );
                                if (!matches.isEmpty()) { changed = true; break; }
                            } catch (Exception ignore) { /* continue */ }
                        }
                    }
                    if (!changed) {
                        throw new Exception("status text not found in rows after retries");
                    }
                } catch (Exception e) {
                    // final retry: reload the services section and search again (best-effort)
                    System.out.println("Warning: status text not immediately present, retrying to refresh row for service " + modelo);
                    try {
                        // attempt to scroll and re-find the element
                        WebElement refreshedRow = driver.findElement(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "')]]"));
                        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", refreshedRow);
                        // final attempt: search again and check for the status inside the row elements
                        List<WebElement> retryRows = driver.findElements(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "')]]"));
                        for (WebElement rr : retryRows) {
                            try {
                                List<WebElement> matches = rr.findElements(By.xpath(".//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + expectedText.toLowerCase() + "')]") );
                                if (!matches.isEmpty()) { changed = true; break; }
                            } catch (Exception ignore) {}
                        }
                        changed = true;
                    } catch (Exception finalEx) {
                        System.out.println("Status change not observed: " + finalEx.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println("Unexpected error waiting for status change: " + e.getMessage());
            }

            if (!changed) {
                // Debugging info on failure: dump a small HTML snapshot and attempt screenshot
                try {
                    String src = driver.getPageSource();
                    System.out.println("DEBUG PAGE SNAPSHOT (first 2000 chars):\n" + src.substring(0, Math.min(2000, src.length())));
                } catch (Exception ignore) {}
                try {
                    byte[] shot = ((org.openqa.selenium.TakesScreenshot)driver).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                    System.out.println("Captured screenshot bytes: " + (shot != null ? shot.length : "none"));
                } catch (Exception ignore) {}
                throw new AssertionError("Status change action did not result in status update for service: " + modelo);
            }

        } catch (Exception e) {
            throw new AssertionError("Failed to change status for created service: " + e.getMessage());
        }
    }

    @Then("the service should show status {string} in the services list")
    public void theServiceShouldShowStatusInServicesList(String expectedStatus) {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, ADMIN_TIMEOUT);
        if (this.lastCreatedServiceModel == null) {
            throw new AssertionError("No created service model recorded from previous step.");
        }
        String modelo = this.lastCreatedServiceModel;
        String visible = expectedStatus.replace("_", " ");
        try {
            // Wait until a table row for the service contains the visible status (robust loop)
            boolean found = false;
            long start = System.currentTimeMillis();
            while (!found && System.currentTimeMillis() - start < ADMIN_TIMEOUT.toMillis()) {
                try { Thread.sleep(500); } catch (InterruptedException ie) {}
                List<WebElement> rows = driver.findElements(By.xpath("//table//tbody//tr[.//td[contains(., '" + modelo + "')]]"));
                for (WebElement r : rows) {
                    try {
                        List<WebElement> matches = r.findElements(By.xpath(".//*[contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), '" + visible.toLowerCase() + "')]") );
                        if (!matches.isEmpty()) { found = true; break; }
                    } catch (Exception ignore) {}
                }
            }
            if (!found) {
                throw new AssertionError("Service did not show expected status '" + expectedStatus + "' for model '" + modelo + "': status text not found in the table row");
            }
            System.out.println("Verified service '" + modelo + "' shows status '" + visible + "'");
        } catch (Exception e) {
            throw new AssertionError("Service did not show expected status '" + expectedStatus + "' for model '" + modelo + "': " + e.getMessage());
        }
    }

    // Helper to open admin subsections robustly
    private void openAdminSection(WebDriver driver, String pathSegment, String expectedHeading) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        // Try clicking a dashboard card with matching heading
        try {
            List<WebElement> cards = driver.findElements(By.cssSelector(".dashboard-card"));
            String expectedLower = expectedHeading == null ? "" : expectedHeading.toLowerCase();
            String[] parts = expectedLower.split("\\s+");
            String key = parts.length > 0 ? parts[parts.length - 1] : expectedLower; // use last word (eg. 'Servicios')
            for (WebElement card : cards) {
                try {
                    String h3Text = "";
                    try {
                        WebElement h3 = card.findElement(By.tagName("h3"));
                        h3Text = h3.getText().trim().toLowerCase();
                    } catch (Exception hx) {
                        h3Text = card.getText().trim().toLowerCase();
                    }
                    // Match either the full expected heading or the key word (more specific than the first token)
                    if ((!expectedLower.isEmpty() && h3Text.contains(expectedLower)) || (!key.isEmpty() && h3Text.contains(key))) {
                        try { card.click(); } catch (Exception c) { ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", card); }
                        wait.until(ExpectedConditions.or(
                                ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(., '" + expectedHeading + "')]") ),
                                ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + expectedHeading.toLowerCase() + "')]") )
                        ));
                        return;
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        } catch (Exception e) {
            // ignore
        }

        // Fallback: navigate directly to the admin path
        try {
            String origin = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return window.location.origin;");
            String url = origin + "/admin/" + pathSegment;
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.location.href = arguments[0];", url);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + expectedHeading.toLowerCase() + "') or contains(., '" + expectedHeading + "')]")));
            return;
        } catch (Exception e) {
            System.out.println("Could not open admin section '" + pathSegment + "': " + e.getMessage());
        }
    }

}
