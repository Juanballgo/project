package co.com.electromovil.tasks;

import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;

public class IngresarDatos {
    
    private static final Target NOMBRE_FIELD = Target.the("campo de nombre")
        .locatedBy("#nombre");
    private static final Target EMAIL_FIELD = Target.the("campo de email")
        .locatedBy("#email");
    private static final Target TELEFONO_FIELD = Target.the("campo de teléfono")
        .locatedBy("#telefono");
    private static final Target DIRECCION_FIELD = Target.the("campo de dirección")
        .locatedBy("#direccion");
    private static final Target PASSWORD_FIELD = Target.the("campo de contraseña")
        .locatedBy("#password");

    public static Task datosDeUsuario(String nombre, String email, String telefono, String direccion, String password) {
        return Task.where("{0} ingresa los datos del usuario",
            Enter.theValue(nombre).into(NOMBRE_FIELD),
            Enter.theValue(email).into(EMAIL_FIELD),
            Enter.theValue(telefono).into(TELEFONO_FIELD),
            Enter.theValue(direccion).into(DIRECCION_FIELD),
            Enter.theValue(password).into(PASSWORD_FIELD)
        );
    }

    public static Task datosDeServicio(String nombre, String descripcion, String precio) {
        return Task.where("{0} ingresa los datos del servicio",
            Enter.theValue(nombre).into(Target.the("nombre del servicio").locatedBy("#nombre-servicio")),
            Enter.theValue(descripcion).into(Target.the("descripción del servicio").locatedBy("#descripcion")),
            Enter.theValue(precio).into(Target.the("precio del servicio").locatedBy("#precio"))
        );
    }

    public static Task credenciales(String email, String password) {
        return Task.where("{0} ingresa sus credenciales",
            Enter.theValue(email).into(EMAIL_FIELD),
            Enter.theValue(password).into(PASSWORD_FIELD),
            Click.on(Target.the("botón de login").locatedBy("button[type='submit']"))
        );
    }
}