package co.com.electromovil.userinterface;

import net.serenitybdd.screenplay.targets.Target;

public class LoginPage {
    public static final Target LOGIN_FORM = Target.the("formulario de login")
            .locatedBy(".form-box.login");
            
    public static final Target EMAIL_FIELD = Target.the("campo de email")
            .locatedBy("#email");
            
    public static final Target PASSWORD_FIELD = Target.the("campo de contraseña")
            .locatedBy("#password");
            
    public static final Target LOGIN_BUTTON = Target.the("botón de inicio de sesión")
            .locatedBy("#btn");
            
    // Cambiando el selector para que coincida con las clases reales en el frontend
    public static final Target HEADER_PRINCIPAL = Target.the("encabezado de la página principal")
            .locatedBy(".compact-header");
            
    public static final Target MENSAJE_ERROR = Target.the("mensaje de error")
            .locatedBy(".error-message");

    public static String URL = "http://localhost:3000/LoginRegister";
}