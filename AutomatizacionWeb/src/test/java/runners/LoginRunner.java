package runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        // Run only the login-related scenarios to avoid duplicating other feature runs
        features = "src/test/resources/features/login.feature",
        glue = {"stepdefinitions", "hooks"},
        plugin = {
                "pretty",
                "json:target/cucumber-login.json"
        },
        monochrome = true
)
public class LoginRunner {
}