package co.com.electromovil.stepdefinitions;

import co.com.electromovil.models.Credenciales;
import co.com.electromovil.tasks.AbrirPagina;
import co.com.electromovil.tasks.IniciarSesion;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import co.com.electromovil.userinterface.LoginPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class LoginStepDefinitions {
    
    private static final String ACTOR_NAME = "usuario";
    private final Actor actor;
    
    @Managed(driver = "chrome")
    private WebDriver navegador;
    
    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }
    
    public LoginStepDefinitions() {
        this.actor = Actor.named(ACTOR_NAME);
    }

    @Dado("que el usuario se encuentra en la página de inicio de sesión de Electromovil")
    public void queElUsuarioSeEncuentraEnLaPaginaDeInicioSesion() {
        actor.can(BrowseTheWeb.with(navegador));
        actor.wasAbleTo(
            AbrirPagina.enLaPaginaDeLogin()
        );
    }

    @Cuando("ingrese las credenciales correctas")
    public void ingreseCredencialesCorrectas(DataTable dataTable) {
        List<Map<String, String>> datos = dataTable.asMaps();
        Credenciales credenciales = new Credenciales(
            datos.get(0).get("email"),
            datos.get(0).get("password")
        );
        
        actor.attemptsTo(
            IniciarSesion.conCredenciales(credenciales)
        );
    }

    @Cuando("ingrese credenciales incorrectas")
    public void ingreseCredencialesIncorrectas(DataTable dataTable) {
        List<Map<String, String>> datos = dataTable.asMaps();
        Credenciales credenciales = new Credenciales(
            datos.get(0).get("email"),
            datos.get(0).get("password")
        );
        
        actor.attemptsTo(
            IniciarSesion.conCredenciales(credenciales)
        );
    }

    @Entonces("debería ver la página principal del sistema")
    public void deberiaVerLaPaginaPrincipal() {
        actor.attemptsTo(
            WaitUntil.the(LoginPage.HEADER_PRINCIPAL, isVisible()).forNoMoreThan(60).seconds(),
            Ensure.that(LoginPage.HEADER_PRINCIPAL).isDisplayed()
        );
    }

    @Entonces("debería ver la página de su rol")
    public void deberiaVerLaPaginaDeSuRol() {
        // En la aplicación real la navegación lleva a /admin, /tecnico o /usuario.
        // Comprobamos que el encabezado principal esté visible en la página destino.
        actor.attemptsTo(
            WaitUntil.the(LoginPage.HEADER_PRINCIPAL, isVisible()).forNoMoreThan(60).seconds(),
            Ensure.that(LoginPage.HEADER_PRINCIPAL).isDisplayed()
        );
    }

    @Entonces("debería ver un mensaje de error de autenticación")
    public void deberiaVerMensajeError() {
        actor.attemptsTo(
            WaitUntil.the(LoginPage.MENSAJE_ERROR, isVisible()).forNoMoreThan(60).seconds(),
            Ensure.that(LoginPage.MENSAJE_ERROR).isDisplayed()
        );
    }
}