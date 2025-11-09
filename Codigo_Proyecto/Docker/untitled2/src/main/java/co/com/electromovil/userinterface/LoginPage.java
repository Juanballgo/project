package co.com.electromovil.userinterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {
    public static final Target INPUT_EMAIL = Target.the("campo de email")
            .located(By.id("email"));
    
    public static final Target INPUT_PASSWORD = Target.the("campo de contraseña")
            .located(By.id("password"));
    
    public static final Target BOTON_LOGIN = Target.the("botón de inicio de sesión")
            .located(By.id("btn"));
    
    // Mensaje de error que aparece dentro del formulario de login (no tiene id, usa la clase `error-message`)
    public static final Target MENSAJE_ERROR = Target.the("mensaje de error")
            .located(By.cssSelector(".form-box.login .error-message"));

    // Encabezado principal que aparece después del login en las vistas de usuario/admin/técnico
    // (la aplicación usa la clase `compact-header` en las páginas posteriores)
    public static final Target HEADER_PRINCIPAL = Target.the("encabezado de la página principal")
            .located(By.className("compact-header"));
}