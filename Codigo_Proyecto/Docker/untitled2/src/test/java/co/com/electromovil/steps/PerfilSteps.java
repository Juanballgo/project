package co.com.electromovil.steps;

import co.com.electromovil.config.WebDriverConfig;
import co.com.electromovil.pages.PerfilPage;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class PerfilSteps {
    private final PerfilPage perfilPage;

    public PerfilSteps() {
        perfilPage = new PerfilPage(WebDriverConfig.getDriver());
    }

    @Dado("está en la página de perfil")
    public void estaEnPaginaPerfil() {
        // La navegación se maneja en hooks o pasos previos
    }

    @Cuando("edite su información personal")
    public void editeInformacionPersonal(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> userData = data.get(0);
        
        perfilPage.updateProfile(
            userData.get("nombre"),
            userData.get("telefono"),
            userData.get("direccion")
        );
    }

    @Entonces("debería ver un mensaje de actualización exitosa")
    public void deberiaVerMensajeActualizacion() {
        String message = perfilPage.getSuccessMessage();
        Assert.assertTrue("No se mostró el mensaje de actualización exitosa",
            message.contains("Perfil actualizado exitosamente"));
    }

    @Cuando("seleccione cambiar contraseña")
    public void seleccioneCambiarContraseña() {
        // El click se maneja en el siguiente paso
    }

    @Y("complete el formulario de cambio de contraseña")
    public void completeFormularioCambioContraseña(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> passwordData = data.get(0);
        
        perfilPage.changePassword(
            passwordData.get("actualPassword"),
            passwordData.get("nuevaPassword"),
            passwordData.get("confirmarPassword")
        );
    }

    @Entonces("debería ver un mensaje de cambio de contraseña exitoso")
    public void deberiaVerMensajeCambioContraseña() {
        String message = perfilPage.getSuccessMessage();
        Assert.assertTrue("No se mostró el mensaje de cambio de contraseña exitoso",
            message.contains("Contraseña actualizada exitosamente"));
    }

    @Cuando("seleccione la sección {string}")
    public void seleccioneSeccion(String seccion) {
        if ("Mis Electrodomésticos".equals(seccion)) {
            perfilPage.navigateToElectrodomesticos();
        }
    }

    @Entonces("debería ver la opción de agregar nuevo electrodoméstico")
    public void deberiaVerOpcionAgregarElectrodomestico() {
        Assert.assertTrue("No se muestra el botón de agregar electrodoméstico",
            perfilPage.isAddApplianceButtonVisible());
    }
}