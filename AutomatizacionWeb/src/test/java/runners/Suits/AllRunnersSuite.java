package runners.Suits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import runners.AdminRunner;
import runners.LoginRunner;
import runners.UsuarioRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AdminRunner.class,
        LoginRunner.class,
        UsuarioRunner.class
})
public class AllRunnersSuite {
}
