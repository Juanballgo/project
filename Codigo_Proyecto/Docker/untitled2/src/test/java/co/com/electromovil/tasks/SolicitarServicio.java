package co.com.electromovil.tasks;

import co.com.electromovil.userinterface.UsuarioPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.screenplay.actions.Clear;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SolicitarServicio implements Task {
    private final String tipoEquipo;
    private final String marca;
    private final String modelo;
    private final String descripcion;
    private final String fecha;

    public SolicitarServicio(String tipoEquipo, String marca, String modelo, String descripcion, String fecha) {
        this.tipoEquipo = tipoEquipo;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public static SolicitarServicio conDatos(String tipoEquipo, String marca, String modelo, String descripcion, String fecha) {
        return new SolicitarServicio(tipoEquipo, marca, modelo, descripcion, fecha);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(UsuarioPage.SERVICE_FORM, isVisible()).forNoMoreThan(10).seconds(),
            Scroll.to(UsuarioPage.SERVICE_FORM),
            WaitUntil.the(UsuarioPage.TIPO_EQUIPO, isVisible()).forNoMoreThan(5).seconds(),
            SelectFromOptions.byValue(tipoEquipo).from(UsuarioPage.TIPO_EQUIPO),
            Clear.field(UsuarioPage.MARCA),
            Enter.theValue(marca).into(UsuarioPage.MARCA),
            Clear.field(UsuarioPage.MODELO),
            Enter.theValue(modelo).into(UsuarioPage.MODELO),
            Clear.field(UsuarioPage.DESCRIPCION_PROBLEMA),
            Enter.theValue(descripcion).into(UsuarioPage.DESCRIPCION_PROBLEMA),
            Clear.field(UsuarioPage.FECHA_SOLICITUD),
            Enter.theValue(fecha).into(UsuarioPage.FECHA_SOLICITUD),
            WaitUntil.the(UsuarioPage.SUBMIT_SERVICIO, isVisible()).forNoMoreThan(5).seconds(),
            Scroll.to(UsuarioPage.SUBMIT_SERVICIO),
            Click.on(UsuarioPage.SUBMIT_SERVICIO)
        );
    }
}
