package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class ReportarAvancesPage {

    // Menú Reportar Avances - es un <h2>
    public static final Target MENU_REPORTAR_AVANCES = Target.the("Menú Reportar Avances")
            .located(By.xpath("//h2[contains(text(),'Reportar Avances')]"));

    // Select de Servicios
    public static final Target SELECT_SERVICIO = Target.the("Select de Servicios")
            .located(By.name("servicio"));

    // Select de Tipo de Reporte
    public static final Target SELECT_TIPO_REPORTE = Target.the("Select de Tipo de Reporte")
            .located(By.name("tipo_reporte"));

    // Campo de Detalles
    public static final Target CAMPO_DETALLES = Target.the("Campo de detalles")
            .located(By.name("detalles"));

    // Campo de Costo (tiene name="costo_mostrar")
    public static final Target CAMPO_COSTO = Target.the("Campo de costo")
            .located(By.name("costo_mostrar"));

    // Botón Enviar Reporte
    public static final Target BOTON_ENVIAR_REPORTE = Target.the("Botón Enviar Reporte")
            .located(By.xpath("//button[contains(text(),'Enviar Reporte')]"));
}