package co.com.electromovil.steps;

import co.com.electromovil.config.WebDriverConfig;
import co.com.electromovil.pages.AdminPage;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class AdminSteps {
    private final AdminPage adminPage;

    public AdminSteps() {
        adminPage = new AdminPage(WebDriverConfig.getDriver());
    }

    @Dado("que el usuario está autenticado como administrador")
    public void usuarioAutenticadoComoAdmin() {
        // La autenticación se maneja en hooks o pasos previos
    }

    @Dado("está en el panel de administración")
    public void estaEnPanelAdmin() {
        // La navegación se maneja en hooks o pasos previos
    }

    @Cuando("seleccione la sección {string}")
    public void seleccioneSeccionAdmin(String seccion) {
        // La navegación entre secciones se maneja en la página
    }

    @Entonces("debería ver la lista de usuarios registrados")
    public void deberiaVerListaUsuarios() {
        List<WebElement> usuarios = adminPage.getUsersList();
        Assert.assertFalse("No se muestran usuarios en la lista",
            usuarios.isEmpty());
    }

    @Y("debería poder buscar usuarios por nombre o email")
    public void deberiaBuscarUsuarios() {
        adminPage.searchUser("test@example.com");
        List<WebElement> resultados = adminPage.getUsersList();
        Assert.assertFalse("La búsqueda no muestra resultados",
            resultados.isEmpty());
    }

    @Y("debería poder cambiar el rol de un usuario")
    public void deberiaCambiarRolUsuario() {
        List<WebElement> usuarios = adminPage.getUsersList();
        if (!usuarios.isEmpty()) {
            String userId = usuarios.get(0).getAttribute("data-user-id");
            adminPage.changeUserRole(userId, "tecnico");
            String message = adminPage.getSuccessMessage();
            Assert.assertTrue("No se mostró mensaje de éxito al cambiar rol",
                message.contains("Rol actualizado exitosamente"));
        }
    }

    @Y("asigne un técnico al servicio")
    public void asigneTecnicoServicio() {
        List<WebElement> servicios = adminPage.getPendingServicesList();
        if (!servicios.isEmpty()) {
            String serviceId = servicios.get(0).getAttribute("data-service-id");
            adminPage.assignTechnicianToService(serviceId, "1"); // ID del técnico
            String message = adminPage.getSuccessMessage();
            Assert.assertTrue("No se mostró mensaje de asignación exitosa",
                message.contains("Técnico asignado exitosamente"));
        }
    }

    @Y("seleccione el rango de fechas")
    public void seleccioneRangoFechas(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps();
        Map<String, String> fechas = data.get(0);
        
        adminPage.setReportDateRange(
            fechas.get("fechaInicio"),
            fechas.get("fechaFin")
        );
    }

    @Entonces("debería ver las estadísticas de servicios")
    public void deberiaVerEstadisticas() {
        adminPage.generateReport();
        // Verificar que las estadísticas se muestran
    }

    @Y("debería poder exportar el reporte")
    public void deberiaExportarReporte() {
        adminPage.exportReport();
        String message = adminPage.getSuccessMessage();
        Assert.assertTrue("No se mostró mensaje de exportación exitosa",
            message.contains("Reporte exportado exitosamente"));
    }
}