package co.com.electromovil.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

public class LoginAs implements Interaction {
    private static final Logger logger = LoggerFactory.getLogger(LoginAs.class);
    
    private final Target emailField;
    private final Target passwordField;
    private final Target submitButton;
    private final String email;
    private final String password;

    public LoginAs(Target emailField, Target passwordField, Target submitButton, String email, String password) {
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.submitButton = submitButton;
        this.email = email;
        this.password = password;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        logger.info("Iniciando proceso de login para {}", email);
        
        actor.attemptsTo(
            // Limpiar campos
            Clear.field(emailField),
            Clear.field(passwordField),
            
            // Esperar a que los campos estén listos
            WaitUntil.the(emailField, isEnabled()).forNoMoreThan(60).seconds(),
            WaitUntil.the(passwordField, isEnabled()).forNoMoreThan(60).seconds(),
            
            // Ingresar credenciales
            Enter.theValue(email).into(emailField),
            Enter.theValue(password).into(passwordField),
            
            // Esperar y hacer clic en el botón
            WaitUntil.the(submitButton, isClickable()).forNoMoreThan(60).seconds(),
            Click.on(submitButton)
        );
        
        logger.info("Proceso de login completado para {}", email);
    }

    public static LoginAs withCredentials(Target emailField, Target passwordField, Target submitButton, String email, String password) {
        return instrumented(LoginAs.class, emailField, passwordField, submitButton, email, password);
    }
}