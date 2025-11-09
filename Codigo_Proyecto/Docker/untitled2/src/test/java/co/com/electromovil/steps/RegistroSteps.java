package co.com.electromovil.steps;

import co.com.electromovil.config.WebDriverConfig;
import co.com.electromovil.pages.RegistroPage;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;

import java.util.Map;
import java.util.List;

public class RegistroSteps {
    private final RegistroPage registroPage;

    public RegistroSteps() {
        registroPage = new RegistroPage(WebDriverConfig.getDriver());
    }

    @Dado("que el visitante está en la página de registro")
    public void elVisitanteEstaEnLaPaginaDeRegistro() {
        registroPage.navigateToRegistro();
    }

    @Cuando("ingrese los datos del nuevo usuario")
    public void ingreseLosDatosDelNuevoUsuario(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> userData = data.get(0);
        
        registroPage.fillRegistrationForm(
            userData.get("nombre"),
            userData.get("email"),
            userData.get("telefono"),
            userData.get("direccion"),
            userData.get("password"),
            userData.get("confirmarPassword")
        );
    }

    @Cuando("hace clic en el botón de registro")
    public void haceClicEnElBotonDeRegistro() {
        registroPage.clickRegisterButton();
    }

    @Entonces("debería ver un mensaje de registro exitoso")
    public void deberiaVerMensajeDeRegistroExitoso() {
        String message = registroPage.getSuccessMessage();
        Assert.assertTrue("No se mostró el mensaje de éxito", 
            message.contains("Registro exitoso"));
    }

    @Entonces("debería ser redirigido a la página de inicio de sesión")
    public void deberiaSerRedirigidoALogin() {
        Assert.assertTrue("No fue redirigido a la página de login",
            registroPage.isOnLoginPage());
    }

    @Cuando("ingrese los datos con contraseñas diferentes")
    public void ingreseDatosConContraseñasDiferentes(DataTable dataTable) {
        ingreseLosDatosDelNuevoUsuario(dataTable);
    }

    @Entonces("debería ver un mensaje de error indicando que las contraseñas no coinciden")
    public void deberiaVerMensajeErrorContraseñas() {
        String error = registroPage.getErrorMessage();
        Assert.assertTrue("No se mostró el mensaje de error correcto",
            error.contains("Las contraseñas no coinciden"));
    }

    @Cuando("ingrese un email que ya existe en el sistema")
    public void ingreseEmailExistente(DataTable dataTable) {
        ingreseLosDatosDelNuevoUsuario(dataTable);
    }

    @Entonces("debería ver un mensaje indicando que el email ya está registrado")
    public void deberiaVerMensajeEmailRegistrado() {
        String error = registroPage.getErrorMessage();
        Assert.assertTrue("No se mostró el mensaje de error correcto",
            error.contains("El correo electrónico ya está registrado"));
    }
}