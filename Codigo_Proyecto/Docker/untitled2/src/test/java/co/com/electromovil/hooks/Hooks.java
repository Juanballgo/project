package co.com.electromovil.hooks;

import co.com.electromovil.config.WebDriverConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

public class Hooks {
    
    @Before
    public void setUp() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled("usuario")
               .can(BrowseTheWeb.with(WebDriverConfig.getDriver()));
    }

    @After
    public void tearDown() {
        WebDriverConfig.quitDriver();
        OnStage.drawTheCurtain();
    }
}