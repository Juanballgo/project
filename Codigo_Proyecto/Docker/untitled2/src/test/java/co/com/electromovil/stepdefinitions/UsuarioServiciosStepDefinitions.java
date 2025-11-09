package co.com.electromovil.stepdefinitions;

import co.com.electromovil.models.Credenciales;
import co.com.electromovil.tasks.AbrirPagina;
import co.com.electromovil.tasks.IniciarSesion;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;
import io.cucumber.java.Before;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

import co.com.electromovil.userinterface.UsuarioPage;

import co.com.electromovil.tasks.SolicitarServicio;
import co.com.electromovil.tasks.AgregarElectrodomestico;

public class UsuarioServiciosStepDefinitions {
    private static final String ACTOR_NAME = "cliente";
    private final Actor actor;

    @Managed(driver = "chrome")
    private WebDriver navegador;

    public UsuarioServiciosStepDefinitions() {
        this.actor = Actor.named(ACTOR_NAME);
    }

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new net.serenitybdd.screenplay.actors.OnlineCast());
    }

    @Dado("que el usuario cliente está logueado correctamente")
    public void queElUsuarioClienteEstaLogueado() {
        actor.can(BrowseTheWeb.with(navegador));
        actor.wasAbleTo(
            AbrirPagina.enLaPaginaDeLogin(),
            IniciarSesion.conCredenciales(new Credenciales("juanpoxc@gmail.com", "147258369po"))
        );
        // Esperar a que cargue la vista de usuario
        actor.attemptsTo(
            WaitUntil.the(".compact-header", isVisible()).forNoMoreThan(30).seconds()
        );
    }

    @Cuando("el usuario completa el formulario de solicitud de servicio")
    public void elUsuarioSolicitaServicio() {
        String hoy = java.time.LocalDate.now().toString();
        actor.attemptsTo(
            SolicitarServicio.conDatos(
                "lavadora", // tipoEquipo
                "LG",      // marca
                "TurboWash", // modelo
                "Hace ruido al centrifugar", // descripcion
                hoy // fecha
            )
        );
    }

    // Target para la lista de servicios
    // (agregar en UsuarioPage si no existe)
    @Entonces("debería ver el nuevo servicio en la lista de servicios")
    public void deberiaVerNuevoServicio() {
        actor.attemptsTo(
            WaitUntil.the(UsuarioPage.SERVICIOS_LIST, isVisible()).forNoMoreThan(10).seconds(),
            Ensure.that(UsuarioPage.SERVICIOS_LIST).text().contains("LG")
        );
    }

    @Cuando("el usuario completa el formulario para añadir un electrodoméstico")
    public void elUsuarioAñadeElectrodomestico() {
        String fechaCompra = "15/06/2024";
        actor.attemptsTo(
            AgregarElectrodomestico.conDatos(
                "nevera", // tipo
                "Samsung", // marca
                "RT38K5930", // modelo
                fechaCompra // fechaCompra
            )
        );
    }

    @Entonces("debería ver el nuevo electrodoméstico en la lista")
    public void deberiaVerNuevoElectrodomestico() {
        String marca = System.getProperty("test.appliance.brand", "Samsung");
        boolean found = false;
    for (int i = 0; i < 40; i++) { // 40 intentos, 500ms cada uno = 20s
            actor.attemptsTo(WaitUntil.the(UsuarioPage.APPLIANCES_GRID, isVisible()).forNoMoreThan(2).seconds());
            String gridText = UsuarioPage.APPLIANCES_GRID.resolveFor(actor).getText();
            if (gridText.contains(marca)) {
                found = true;
                break;
            }
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
        if (!found) {
            throw new AssertionError("No se encontró el electrodoméstico agregado con marca: " + marca);
        }
    }
}
