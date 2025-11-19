package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/admin.feature",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "json:target/cucumber-admin.json"
        },
        monochrome = true
)
public class AdminRunner {
}
