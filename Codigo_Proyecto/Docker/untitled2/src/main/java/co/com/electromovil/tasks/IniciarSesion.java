package co.com.electromovil.tasks;

import co.com.electromovil.models.Credenciales;
import co.com.electromovil.userinterface.LoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class IniciarSesion implements Task {

    private final Credenciales credenciales;

    public IniciarSesion(Credenciales credenciales) {
        this.credenciales = credenciales;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Enter.theValue(credenciales.getEmail()).into(LoginPage.INPUT_EMAIL),
            Enter.theValue(credenciales.getPassword()).into(LoginPage.INPUT_PASSWORD),
            Click.on(LoginPage.BOTON_LOGIN)
        );
    }

    public static IniciarSesion conCredenciales(Credenciales credenciales) {
        return Tasks.instrumented(IniciarSesion.class, credenciales);
    }
}