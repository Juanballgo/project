package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static userinterfaces.LoginPage.*;

public class IniciarSesion implements Task {

    private String correo;
    private String clave;

    public IniciarSesion(String correo, String clave) {
        this.correo = correo;
        this.clave = clave;
    }

    public static IniciarSesion conCredenciales(String correo, String clave) {
        return instrumented(IniciarSesion.class, correo, clave);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Open.url("http://localhost:3000/LoginRegister"),
                WaitUntil.the(CAMPO_CORREO, isVisible()).forNoMoreThan(10).seconds(),
                Enter.theValue(correo).into(CAMPO_CORREO),
                Enter.theValue(clave).into(CAMPO_CLAVE),
                Click.on(BOTON_LOGIN)
        );
    }
}