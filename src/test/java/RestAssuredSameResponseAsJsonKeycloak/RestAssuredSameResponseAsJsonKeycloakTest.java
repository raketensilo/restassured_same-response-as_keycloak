package RestAssuredSameResponseAsJsonKeycloak;

import org.junit.Test;

import static io.restassured.RestAssured.given;


public class RestAssuredSameResponseAsJsonKeycloakTest extends AbstractRestAssuredSameResponseAsJsonKeycloakTest {

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
                get("/admin/realms/{realm}/roles", kc.get("realm")).
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
                get("/admin/realms/{realm}/users", kc.get("realm")).
        then().
                spec(store.getLogSpec()).
        assertThat().
                statusCode(200);

        assertJson.sameJsonAs();

    }

}
