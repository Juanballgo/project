package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PerfilPage {

    // Menú o enlace al perfil técnico
    public static final Target MENU_PERFIL_TECNICO = Target.the("Menú Perfil Técnico")
            .located(By.xpath("//a[contains(text(),'Perfil Técnico')]"));
}