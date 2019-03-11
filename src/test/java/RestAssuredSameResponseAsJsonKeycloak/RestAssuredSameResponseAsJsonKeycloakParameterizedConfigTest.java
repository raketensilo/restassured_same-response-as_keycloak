package RestAssuredSameResponseAsJsonKeycloak;

import org.junit.Test;

import static io.restassured.RestAssured.given;


public class RestAssuredSameResponseAsJsonKeycloakParameterizedConfigTest extends AbstractRestAssuredSameResponseAsJsonKeycloakParameterizedConfigTest {

    @Test
    public void getListOfRealms() {

        given().
        when().
                get("/admin/realms").
        then().
                spec(store.getLogSpec()).
        assertThat().
                statusCode(200);

        assertJson.sameJsonAs();

    }

    @Test
    public void getListOfRolesInMasterRealm() {

        given().
        when().
                get("/admin/realms/master/roles").
        then().
                spec(store.getLogSpec()).
        assertThat().
                statusCode(200);

        assertJson.sameJsonAs();

    }

    @Test
    public void getListofUsersInMasterRealm() {

        given().
        when().
                get("/admin/realms/master/users").
        then().
                spec(store.getLogSpec()).
        assertThat().
                statusCode(200);

        assertJson.sameJsonAs();

    }

}
