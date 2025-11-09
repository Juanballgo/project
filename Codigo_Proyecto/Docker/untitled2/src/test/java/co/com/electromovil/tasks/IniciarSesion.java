package co.com.electromovil.tasks;

import co.com.electromovil.models.Credenciales;
import co.com.electromovil.userinterface.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public class IniciarSesion implements Task {
    private static final Logger logger = LoggerFactory.getLogger(IniciarSesion.class);
    private final Credenciales credenciales;

    public IniciarSesion(Credenciales credenciales) {
        this.credenciales = credenciales;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        logger.info("Iniciando proceso de login para usuario: {}", credenciales.getEmail());
        
        actor.attemptsTo(
            // Esperar y limpiar campos
            WaitUntil.the(LoginPage.EMAIL_FIELD, isVisible()).forNoMoreThan(30).seconds(),
            Clear.field(LoginPage.EMAIL_FIELD),
            Clear.field(LoginPage.PASSWORD_FIELD),
            
            // Ingresar credenciales
            Enter.theValue(credenciales.getEmail()).into(LoginPage.EMAIL_FIELD),
            Enter.theValue(credenciales.getPassword()).into(LoginPage.PASSWORD_FIELD),
            
            // Esperar y hacer clic en el botón
            WaitUntil.the(LoginPage.LOGIN_BUTTON, isClickable()).forNoMoreThan(30).seconds(),
            Click.on(LoginPage.LOGIN_BUTTON)
        );
        
        logger.info("Proceso de login completado para usuario: {}", credenciales.getEmail());
    }

    public static IniciarSesion conCredenciales(Credenciales credenciales) {
        return instrumented(IniciarSesion.class, credenciales);
    }
}