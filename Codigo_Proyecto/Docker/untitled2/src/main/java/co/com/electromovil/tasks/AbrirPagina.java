package co.com.electromovil.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Open;

public class AbrirPagina implements Task {

    private static final String URL_LOGIN = "http://localhost:3000/LoginRegister";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Open.url(URL_LOGIN)
        );
    }

    public static Task enLaPaginaDeLogin() {
        return Tasks.instrumented(AbrirPagina.class);
    }
}