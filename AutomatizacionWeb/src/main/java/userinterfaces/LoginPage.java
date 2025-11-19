package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {
    public static final Target CAMPO_CORREO = Target.the("Campo de correo")
            .located(By.name("email"));

    public static final Target CAMPO_CLAVE = Target.the("Campo de contraseña")
            .located(By.name("password"));

    public static final Target BOTON_LOGIN = Target.the("Botón de login")
            .located(By.xpath("//button[contains(text(),'Iniciar sesion')]"));
}