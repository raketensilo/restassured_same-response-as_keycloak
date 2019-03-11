package RestAssuredStore;

import io.restassured.specification.ResponseSpecification;


public interface RestAssuredStoreInterface {

    RestAssuredStoreInterface setBasePath(String basePath);
    ResponseSpecification getLogSpec();

    String getResponseAsString(String testName, ResponseType responseType);
    Boolean responseExists(String testName, ResponseType responseType);

}
