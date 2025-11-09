package co.com.electromovil.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

public class ElementoEsVisible {
    
    public static Question<Boolean> elemento(Target target) {
        return actor -> target.resolveFor(actor).isVisible();
    }

    public static Question<Boolean> mensaje(String mensaje) {
        Target mensajeTarget = Target.the("mensaje")
            .locatedBy("//*[contains(text(),'" + mensaje + "')]");
        return elemento(mensajeTarget);
    }

    public static Question<Boolean> mensajeError(String mensaje) {
        Target mensajeTarget = Target.the("mensaje de error")
            .locatedBy(".error-message:contains('" + mensaje + "')");
        return elemento(mensajeTarget);
    }

    public static Question<Boolean> mensajeExito(String mensaje) {
        Target mensajeTarget = Target.the("mensaje de éxito")
            .locatedBy(".success-message:contains('" + mensaje + "')");
        return elemento(mensajeTarget);
    }
}