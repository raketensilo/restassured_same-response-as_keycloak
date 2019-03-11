package RestAssuredStore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

import RestAssuredKeycloak.KeycloakExtension;


public class AbstractRestAssuredStoreKeycloakTest {

    protected static KeycloakExtension kc = new KeycloakExtension();

    @Rule
    public RestAssuredStoreExtension store =
            new RestAssuredStoreExtension().setBasePath("./src/test/resources");

    @BeforeClass
    public static void beforeClass() {
        kc.login();
    }

    @AfterClass
    public static void afterClass() {
        kc.logout();
    }

}
