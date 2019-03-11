package RestAssuredSameResponseAsJsonKeycloak;

import org.junit.Rule;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import HamcrestAssertJson.HamcrestAssertJsonExtension;
import RestAssuredKeycloak.KeycloakExtension;
import RestAssuredStore.RestAssuredStoreExtension;


public abstract class AbstractRestAssuredSameResponseAsJsonKeycloakParameterizedConfigTest {

    protected static KeycloakExtension kc =
            new KeycloakExtension().setProperties("kc1.properties");

    @Rule
    public RestAssuredStoreExtension store =
            new RestAssuredStoreExtension().setBasePath("./src/test/resources");

    protected HamcrestAssertJsonExtension assertJson = new HamcrestAssertJsonExtension(store);

    @BeforeClass
    public static void beforeClass() {
        kc.login();
    }

    @AfterClass
    public static void afterClass() {
        kc.logout();
    }

}
