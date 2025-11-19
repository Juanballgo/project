package userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class AdminPage {

    public static Target ADMIN_DASHBOARD = Target.the("admin dashboard").located(By.cssSelector("#admin-dashboard, .admin-dashboard"));

    public static Target USERS_MENU = Target.the("users menu").located(By.cssSelector("a[href*='users'], #menu-users, .menu-users"));
    public static Target USERS_TABLE = Target.the("users table").located(By.cssSelector("table#users, .users-table, .users-list"));

    public static Target SERVICES_MENU = Target.the("services menu").located(By.cssSelector("a[href*='services'], #menu-services, .menu-services"));
    public static Target SERVICES_TABLE = Target.the("services table").located(By.cssSelector("table#services, .services-table, .services-list"));

    public static Target INVOICES_MENU = Target.the("invoices menu").located(By.cssSelector("a[href*='invoices'], #menu-invoices, .menu-invoices"));
    public static Target INVOICES_TABLE = Target.the("invoices table").located(By.cssSelector("table#invoices, .invoices-table, .invoices-list"));

    public static Target REPORTS_MENU = Target.the("reports menu").located(By.cssSelector("a[href*='reports'], #menu-reports, .menu-reports"));
    public static Target REPORTS_DASH = Target.the("reports dash").located(By.cssSelector("#reports-dashboard, .reports-dashboard"));

}
