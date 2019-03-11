package RestAssuredKeycloak;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import MyFileUtils.MyFileUtils;

import static io.restassured.RestAssured.given;


public class KeycloakExtension {

    private Properties p = null;
    private static final String PROPERTIES_RESOURCEPATH_DEFAULT = "keycloak.properties";

    private static KeyValueStore authData = new KeyValueStore();

    private static RequestSpecification originalRequestSpec;

    public  KeycloakExtension setProperties(String propertiesResourcePath) throws RuntimeException {
        this.p = new Properties();

        try {
//            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesResourcePath);
            InputStream inputStream = MyFileUtils.getTestResourceAsStream(propertiesResourcePath);
            this.p.load(inputStream);
            inputStream.close();
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return this;
    }

    public void login() {
        if (p == null) {
            setProperties(PROPERTIES_RESOURCEPATH_DEFAULT);
        }

        RestAssured.baseURI = p.getProperty("baseurl");

        Response response =
                given().
                        header("Content-Type", "application/x-www-form-urlencoded").
                        formParam("client_id",p.get("client_id")).
                        formParam("grant_type",p.get("grant_type")).
                        formParam("username",p.get("username")).
                        formParam("password",p.get("password")).
                        when().
                        post(p.getProperty("endpoint.login")).
                        then().
                        assertThat().
                        statusCode(200).
                        extract().
                        response();

        authData.set(response.getBody().asString());

        // backup current RestAssured RequestSpecification
        originalRequestSpec = RestAssured.requestSpecification;

        // add bearer token to header of each request that RestAssured sends
        RequestSpecification bearerTokenRequestSpecification = new RequestSpecBuilder().
                addHeader("Authorization", "Bearer " + authData.getValue("access_token")).
        build();
        RestAssured.requestSpecification = bearerTokenRequestSpecification;

//        // to have every response logged to disk, add log().body() to the this Response Specification
//        ResponseSpecification alwaysLogResponseSpec = new ResponseSpecBuilder().
//                log(LogDetail.BODY).
//                build();
//
//        // add it to RestAssured's global responseSpecification to have effect on all responses
//        RestAssured.responseSpecification = alwaysLogResponseSpec;
    }

    public void logout() {

        given().
                formParam("client_id",p.get("client_id")).
                formParam("refresh_token", authData.getValue("refresh_token")).
                when().
                post(p.getProperty("endpoint.logout")).
                then().
                assertThat().
                statusCode(204);

        if (originalRequestSpec != null) {
            RestAssured.requestSpecification = originalRequestSpec;
        }

        RestAssured.reset();

    }

    public String get(String key) {
        return p.getProperty(key);
    }

}
