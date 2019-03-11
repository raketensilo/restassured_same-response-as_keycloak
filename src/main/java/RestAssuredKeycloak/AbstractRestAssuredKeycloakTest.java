package RestAssuredKeycloak;

import org.junit.AfterClass;
import org.junit.BeforeClass;


public class AbstractRestAssuredKeycloakTest {

    protected static KeycloakExtension kc = new KeycloakExtension();

    @BeforeClass
    public static void beforeClass() {
        kc.login();
    }

    @AfterClass
    public static void afterClass() {
        kc.logout();
    }

}
