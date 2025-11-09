package co.com.electromovil.steps;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

public class TestHooks {
    
    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }
}