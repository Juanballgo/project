package co.com.electromovil.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSteps {
    private static final Logger logger = LoggerFactory.getLogger(LoginSteps.class);

    private static final Target LOGIN_FORM = Target.the("formulario de login")
        .locatedBy(".form-box.login");
    private static final Target USERNAME_FIELD = Target.the("campo de email")
        .locatedBy("#email");
    private static final Target PASSWORD_FIELD = Target.the("campo de contraseña")
        .locatedBy("#password");
    private static final Target LOGIN_BUTTON = Target.the("botón de inicio de sesión")
        .locatedBy("#btn");
    private static final Target DASHBOARD_ELEMENT = Target.the("elemento del dashboard")
        .locatedBy("//div[contains(@class, 'dashboard-container') or contains(@class, 'home-container')]");
    private static final Target ERROR_MESSAGE = Target.the("mensaje de error")
        .locatedBy(".error-message");

    @Dado("que el usuario se encuentra en la página de inicio de sesión de Electromovil")
    public void queElUsuarioSeEncuentraEnLaPaginaDeInicioSesion() {
        logger.info("Navegando a la página de login");
        theActorCalled("usuario").wasAbleTo(
            Open.url("http://localhost:3000/LoginRegister")
        );

        logger.info("Esperando que la página de login cargue");
        theActorInTheSpotlight().attemptsTo(
            WaitUntil.the(LOGIN_FORM, isVisible()).forNoMoreThan(30).seconds(),
            WaitUntil.the(USERNAME_FIELD, isClickable()).forNoMoreThan(30).seconds(),
            WaitUntil.the(PASSWORD_FIELD, isClickable()).forNoMoreThan(30).seconds(),
            Ensure.that(LOGIN_FORM).isDisplayed()
        );

        logger.info("Página de login cargada exitosamente");
    }

    @Cuando("ingrese las credenciales correctas")
    public void ingreseCredencialesCorrectas(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        String usuario = data.get(0).get("email");
        String password = data.get(0).get("password");

        logger.info("Iniciando proceso de login con credenciales correctas: {}", usuario);
        
        theActorInTheSpotlight().attemptsTo(
            // Limpiar campos
            net.serenitybdd.screenplay.actions.Clear.field(USERNAME_FIELD),
            net.serenitybdd.screenplay.actions.Clear.field(PASSWORD_FIELD),
            
            // Ingresar credenciales
            net.serenitybdd.screenplay.actions.Enter.theValue(usuario).into(USERNAME_FIELD),
            net.serenitybdd.screenplay.actions.Enter.theValue(password).into(PASSWORD_FIELD),
            
            // Esperar a que el botón esté clickeable y hacer clic
            WaitUntil.the(LOGIN_BUTTON, isClickable()).forNoMoreThan(30).seconds(),
            net.serenitybdd.screenplay.actions.Click.on(LOGIN_BUTTON)
        );
        
        logger.info("Proceso de login completado para: {}", usuario);
    }

    @Cuando("ingrese credenciales incorrectas")
    public void ingreseCredencialesIncorrectas(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        String usuario = data.get(0).get("email");
        String password = data.get(0).get("password");

        logger.info("Iniciando proceso de login con credenciales incorrectas: {}", usuario);
        
        theActorInTheSpotlight().attemptsTo(
            // Limpiar campos
            net.serenitybdd.screenplay.actions.Clear.field(USERNAME_FIELD),
            net.serenitybdd.screenplay.actions.Clear.field(PASSWORD_FIELD),
            
            // Ingresar credenciales
            net.serenitybdd.screenplay.actions.Enter.theValue(usuario).into(USERNAME_FIELD),
            net.serenitybdd.screenplay.actions.Enter.theValue(password).into(PASSWORD_FIELD),
            
            // Esperar a que el botón esté clickeable y hacer clic
            WaitUntil.the(LOGIN_BUTTON, isClickable()).forNoMoreThan(30).seconds(),
            net.serenitybdd.screenplay.actions.Click.on(LOGIN_BUTTON)
        );
        
        logger.info("Proceso de login con credenciales incorrectas completado");
    }

    @Entonces("debería ver la página de su rol")
    public void deberiaVerLaPaginaPrincipal() {
        logger.info("Verificando redirección al dashboard después del login");
        
        theActorInTheSpotlight().attemptsTo(
            // Esperar a que el dashboard sea visible
            WaitUntil.the(DASHBOARD_ELEMENT, isVisible()).forNoMoreThan(30).seconds(),
            
            // Verificar que estamos en el dashboard
            Ensure.that(DASHBOARD_ELEMENT).isDisplayed()
        );
        
        logger.info("Usuario redirigido exitosamente al dashboard");
    }

    @Entonces("debería ver un mensaje de error de autenticación")
    public void deberiaVerMensajeError() {
        logger.info("Verificando mensaje de error de autenticación");
        
        theActorInTheSpotlight().attemptsTo(
            // Esperar a que el mensaje de error sea visible
            WaitUntil.the(ERROR_MESSAGE, isVisible()).forNoMoreThan(30).seconds(),
            
            // Verificar que el mensaje de error está presente
            Ensure.that(ERROR_MESSAGE).isDisplayed()
        );
        
        logger.info("Mensaje de error mostrado correctamente");
    }
}
 