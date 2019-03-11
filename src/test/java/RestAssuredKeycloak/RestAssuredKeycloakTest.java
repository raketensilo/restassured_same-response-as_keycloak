package RestAssuredKeycloak;

import org.junit.Test;

import static io.restassured.RestAssured.given;


public class RestAssuredKeycloakTest extends AbstractRestAssuredKeycloakTest {

    @Test
    public void getListOfRealms() {

        given().
        when().
                get("/admin/realms").
        then().
                assertThat().statusCode(200);

    }

    @Test
    public void getListOfRolesInRealm() {

        given().
        when().
                get("/admin/realms/master/users").
        then().
                assertThat().statusCode(200);

    }

    @Test
    public void getListofUsersInMasterRealm() {

        given().
        when().
                get("/admin/realms/master/users").
        then().
                assertThat().statusCode(200);

    }

}
