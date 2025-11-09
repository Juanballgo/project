
    package co.com.electromovil.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class UsuarioPage {
    // Targets para el formulario de servicio técnico
    public static final Target SERVICE_FORM = Target.the("formulario de servicio técnico").located(By.cssSelector(".service-form"));
    public static final Target TIPO_EQUIPO = Target.the("tipo de equipo").located(By.name("tipo_equipo"));
    public static final Target MARCA = Target.the("marca").located(By.name("marca"));
    public static final Target MODELO = Target.the("modelo").located(By.name("modelo"));
    public static final Target DESCRIPCION_PROBLEMA = Target.the("descripción del problema").located(By.name("descripcion_problema"));
    public static final Target FECHA_SOLICITUD = Target.the("fecha de solicitud").located(By.name("fecha_solicitud"));
    public static final Target SUBMIT_SERVICIO = Target.the("botón crear servicio técnico").located(By.cssSelector(".service-form .submit-btn"));

    // Targets para el formulario de electrodoméstico
    public static final Target ADD_APPLIANCE_BTN = Target.the("botón añadir electrodoméstico").located(By.cssSelector(".add-appliance-btn"));
    public static final Target APPLIANCE_FORM = Target.the("formulario de electrodoméstico").located(By.cssSelector(".appliance-form"));
    public static final Target APPLIANCE_TYPE = Target.the("tipo de electrodoméstico").located(By.name("type"));
    public static final Target APPLIANCE_BRAND = Target.the("marca electrodoméstico").located(By.name("brand"));
    public static final Target APPLIANCE_MODEL = Target.the("modelo electrodoméstico").located(By.name("model"));
    public static final Target APPLIANCE_PURCHASE_DATE = Target.the("fecha de compra").located(By.name("purchaseDate"));
    public static final Target APPLIANCE_IMAGE = Target.the("imagen electrodoméstico").located(By.id("appliance-image"));
    public static final Target SAVE_APPLIANCE_BTN = Target.the("guardar electrodoméstico").located(By.cssSelector(".appliance-form .save-btn"));

    // Target para la lista de servicios
    public static final Target SERVICIOS_LIST = Target.the("lista de servicios").located(By.cssSelector(".servicios-list"));

    // Target para la grilla de electrodomésticos
    public static final Target APPLIANCES_GRID = Target.the("grilla de electrodomésticos").located(By.cssSelector(".appliances-grid"));
}
