package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class UsuarioPage {

    // Botón de perfil en header
    public static final Target BUTTON_PROFILE = Target.the("Boton Perfil").located(By.cssSelector("button.profile-btn"));

    // Botones generales
    public static final Target BUTTON_LOGOUT = Target.the("Boton Salir").located(By.cssSelector("button.logout-btn"));

    // Modal perfil: inputs
    public static final Target INPUT_NAME = Target.the("Input nombre").located(By.name("name"));
    public static final Target INPUT_EMAIL = Target.the("Input email").located(By.name("email"));
    public static final Target INPUT_PHONE = Target.the("Input telefono").located(By.name("phone"));
    public static final Target INPUT_ADDRESS = Target.the("Input direccion").located(By.name("address"));
    public static final Target INPUT_CURRENT_PASSWORD = Target.the("Input password actual").located(By.name("current_password"));
    public static final Target INPUT_PASSWORD = Target.the("Input nueva password").located(By.name("password"));
    public static final Target INPUT_PASSWORD_CONFIRM = Target.the("Input confirmar password").located(By.name("password_confirmation"));
    public static final Target BUTTON_SAVE = Target.the("Guardar cambios").located(By.cssSelector("button.save-btn"));

    // Appliances
    public static final Target BUTTON_ADD_APPLIANCE = Target.the("Boton añadir appliance").located(By.cssSelector("button.add-appliance-btn"));
    public static final Target FORM_APPLIANCE = Target.the("Formulario appliance").located(By.cssSelector("form.appliance-form"));
    public static final Target INPUT_APPLIANCE_TYPE = Target.the("Select tipo").located(By.cssSelector("select[name='type']"));
    public static final Target INPUT_APPLIANCE_BRAND = Target.the("Input marca").located(By.cssSelector("input[name='brand']"));
    public static final Target INPUT_APPLIANCE_MODEL = Target.the("Input modelo").located(By.cssSelector("input[name='model']"));
    public static final Target INPUT_APPLIANCE_PURCHASE = Target.the("Input fecha compra").located(By.cssSelector("input[name='purchaseDate']"));
    public static final Target INPUT_APPLIANCE_IMAGE = Target.the("Input imagen").located(By.cssSelector("input#appliance-image"));

    // Appliance card buttons
    public static final Target APPLIANCE_CARD = Target.the("Appliance card").located(By.cssSelector(".appliance-card"));
    public static final Target BUTTON_REQUEST_SERVICE = Target.the("Solicitar servicio").located(By.cssSelector("button.solicitar-servicio-btn"));

    // Service modal
    public static final Target SERVICE_TEXTAREA = Target.the("Descripcion servicio").located(By.cssSelector(".modal-overlay.show textarea"));
    public static final Target SERVICE_SUBMIT = Target.the("Submit servicio").located(By.cssSelector(".modal-overlay.show .save-btn"));
}
