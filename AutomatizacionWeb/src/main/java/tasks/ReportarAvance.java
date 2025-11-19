package tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import static userinterfaces.ReportarAvancesPage.*;

public class ReportarAvance implements Task {

    private String servicio;
    private String tipoReporte;
    private String detalles;
    private String costo;

    public ReportarAvance(String servicio, String tipoReporte, String detalles, String costo) {
        this.servicio = servicio;
        this.tipoReporte = tipoReporte;
        this.detalles = detalles;
        this.costo = costo;
    }

    public static ReportarAvance conDatos(String servicio, String tipoReporte, String detalles, String costo) {
        return instrumented(ReportarAvance.class, servicio, tipoReporte, detalles, costo);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // 1. Ir al menú Reportar Avances (hacer clic en el h2)
                Click.on(MENU_REPORTAR_AVANCES),
                WaitUntil.the(SELECT_SERVICIO, isVisible()).forNoMoreThan(10).seconds(),

                // 2. Seleccionar servicio asignado
                SelectFromOptions.byVisibleText(servicio).from(SELECT_SERVICIO),

                // 3. Seleccionar tipo de reporte - Avance de trabajo
                SelectFromOptions.byVisibleText(tipoReporte).from(SELECT_TIPO_REPORTE),

                // 4. Ingresar detalles
                Enter.theValue(detalles).into(CAMPO_DETALLES),

                // 5. Ingresar costo
                Enter.theValue(costo).into(CAMPO_COSTO),

                // 6. Enviar reporte
                Click.on(BOTON_ENVIAR_REPORTE)
        );
    }
}