package RestAssuredStore;

import org.junit.Test;

import static io.restassured.RestAssured.given;


public class RestAssuredStoreKeycloakTest extends AbstractRestAssuredStoreKeycloakTest {

    @Test
    public void getListOfRealms() {

        given().
        when().
                get("/admin/realms").
        then().
                spec(store.getLogSpec()).
        assertThat().
                statusCode(200);

    }

    @Test
    public void getListOfRolesInRealm() {

        given().
        when().
                get("/admin/realms/master/users").
        then().
                spec(store.getLogSpec()).
        assertThat().
                statusCode(200);

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

    }

}
