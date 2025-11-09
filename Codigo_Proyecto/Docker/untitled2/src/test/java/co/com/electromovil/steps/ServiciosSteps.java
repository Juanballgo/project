package co.com.electromovil.steps;

import co.com.electromovil.config.WebDriverConfig;
import co.com.electromovil.pages.ServiciosPage;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class ServiciosSteps {
    private final ServiciosPage serviciosPage;
    private String lastServiceId;

    public ServiciosSteps() {
        serviciosPage = new ServiciosPage(WebDriverConfig.getDriver());
    }

    @Dado("que el usuario está autenticado como cliente")
    public void usuarioAutenticadoComoCliente() {
        // La autenticación se maneja en hooks o pasos previos
    }

    @Dado("está en la página de servicios")
    public void estaEnPaginaServicios() {
        // La navegación se maneja en hooks o pasos previos
    }

    @Cuando("seleccione {string}")
    public void seleccioneOpcion(String opcion) {
        if ("Solicitar nuevo servicio".equals(opcion)) {
            serviciosPage.clickNuevoServicio();
        }
    }

    @Cuando("complete el formulario de servicio")
    public void completeFormularioServicio(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> servicioData = data.get(0);
        
        serviciosPage.fillServicioForm(
            servicioData.get("tipoEquipo"),
            servicioData.get("marca"),
            servicioData.get("modelo"),
            servicioData.get("descripcionProblema")
        );
    }

    @Y("envíe la solicitud")
    public void envieSolicitud() {
        serviciosPage.submitServicio();
    }

    @Entonces("debería ver un mensaje de confirmación")
    public void deberiaVerMensajeConfirmacion() {
        String message = serviciosPage.getSuccessMessage();
        Assert.assertTrue("No se mostró el mensaje de confirmación",
            message.contains("Servicio solicitado exitosamente"));
    }

    @Y("el servicio debería aparecer en la lista con estado {string}")
    public void servicioDeberiaAparecerEnLista(String estado) {
        List<WebElement> servicios = serviciosPage.getServiciosList();
        Assert.assertFalse("No hay servicios en la lista", servicios.isEmpty());
        
        WebElement ultimoServicio = servicios.get(servicios.size() - 1);
        lastServiceId = ultimoServicio.getAttribute("data-servicio-id");
        String statusActual = serviciosPage.getServicioStatus(lastServiceId);
        
        Assert.assertEquals("El estado del servicio no es el esperado",
            estado, statusActual);
    }

    @Dado("que tiene un servicio en estado {string}")
    public void tieneServicioEnEstado(String estado) {
        List<WebElement> servicios = serviciosPage.getServiciosList();
        boolean encontrado = false;
        
        for (WebElement servicio : servicios) {
            String id = servicio.getAttribute("data-servicio-id");
            if (serviciosPage.getServicioStatus(id).equals(estado)) {
                lastServiceId = id;
                encontrado = true;
                break;
            }
        }
        
        Assert.assertTrue("No se encontró un servicio en estado " + estado,
            encontrado);
    }

    @Cuando("seleccione la opción de cancelar servicio")
    public void seleccioneOpcionCancelar() {
        serviciosPage.cancelarServicio(lastServiceId);
    }

    @Y("confirme la cancelación")
    public void confirmeCancelacion() {
        // La confirmación se maneja en cancelarServicio
    }

    @Entonces("el estado del servicio debería cambiar a {string}")
    public void estadoServicioDeberiaSerCancelado(String estado) {
        String statusActual = serviciosPage.getServicioStatus(lastServiceId);
        Assert.assertEquals("El estado del servicio no cambió a " + estado,
            estado, statusActual);
    }
}