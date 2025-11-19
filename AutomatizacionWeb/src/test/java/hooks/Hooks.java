package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class Hooks {

    @Before
    public void setTheStage(Scenario scenario) {
        OnStage.setTheStage(new OnlineCast());
        System.out.println("=== INICIANDO ESCENARIO: " + scenario.getName() + " ===");
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("=== FINALIZANDO ESCENARIO: " + scenario.getName() + " - ESTADO: " + scenario.getStatus() + " ===");
        OnStage.drawTheCurtain();
    }
}