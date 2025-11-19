package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PerfilPage.MENU_PERFIL_TECNICO; // Necesitarás crear este Page Object

public class AccederPerfilTecnico implements Task {

    public static AccederPerfilTecnico alPerfil() {
        return instrumented(AccederPerfilTecnico.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(MENU_PERFIL_TECNICO)

        );
    }
}