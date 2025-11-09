package co.com.electromovil.config;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.openqa.selenium.WebDriver;

public class TestBase extends PageObject {
    
    @Managed(driver = "chrome")
    private WebDriver navegador;

    @Before(order = 2)
    public void setUpWebDriver() {
        OnStage.theActorInTheSpotlight().can(BrowseTheWeb.with(navegador));
    }
}
