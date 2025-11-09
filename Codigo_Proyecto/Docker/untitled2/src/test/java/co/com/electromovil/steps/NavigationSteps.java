package co.com.electromovil.steps;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class NavigationSteps {

    //private static final Target HOME_LINK = Target.the("enlace de inicio")
      //  .locatedBy("a[href='/']");
    private static final Target SERVICIOS_LINK = Target.the("enlace de servicios")
        .locatedBy("a[href='/servicios']");
    private static final Target PERFIL_LINK = Target.the("enlace de perfil")
        .locatedBy("a[href='/perfil']");
    private static final Target LOGOUT_LINK = Target.the("enlace de cerrar sesión")
        .locatedBy("a[href='/logout']");
    private static final Target NAV_MENU = Target.the("menú de navegación")
        .locatedBy("nav.main-menu");

    @Dado("que el usuario está en la página principal")
    public void queElUsuarioEstaEnLaPaginaPrincipal() {
        theActorCalled("usuario").wasAbleTo(
            Open.url("http://localhost:3000")
        );
    }

    @Cuando("navega a la sección de servicios")
    public void navegaALaSeccionDeServicios() {
        theActorInTheSpotlight().attemptsTo(
            Click.on(SERVICIOS_LINK)
        );
    }

    @Cuando("navega a su perfil")
    public void navegaASuPerfil() {
        theActorInTheSpotlight().attemptsTo(
            Click.on(PERFIL_LINK)
        );
    }

    @Cuando("cierra sesión")
    public void cierraSesion() {
        theActorInTheSpotlight().attemptsTo(
            Click.on(LOGOUT_LINK)
        );
    }

    @Entonces("debería ver el menú de navegación")
    public void deberiaVerElMenuDeNavegacion() {
        theActorInTheSpotlight().attemptsTo(
            Ensure.that(NAV_MENU).isDisplayed()
        );
    }

    @Entonces("debería ver la página principal")
    public void deberiaVerLaPaginaPrincipal() {
        theActorInTheSpotlight().attemptsTo(
            Ensure.that(Target.the("contenido principal").locatedBy(".home-content")).isDisplayed()
        );
    }

    @Entonces("debería ver la sección de servicios")
    public void deberiaVerLaSeccionDeServicios() {
        theActorInTheSpotlight().attemptsTo(
            Ensure.that(Target.the("contenido de servicios").locatedBy(".servicios-content")).isDisplayed()
        );
    }

    @Entonces("debería ver su perfil")
    public void deberiaVerSuPerfil() {
        theActorInTheSpotlight().attemptsTo(
            Ensure.that(Target.the("contenido del perfil").locatedBy(".perfil-content")).isDisplayed()
        );
    }

    @Entonces("debería ser redirigido a la página de inicio de sesión")
    public void deberiaSerRedirigidoALaPaginaDeInicioSesion() {
        theActorInTheSpotlight().attemptsTo(
            Ensure.that(Target.the("página de login").locatedBy(".login-page")).isDisplayed()
        );
    }
}