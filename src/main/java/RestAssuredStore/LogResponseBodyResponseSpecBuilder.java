package RestAssuredStore;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;


public class LogResponseBodyResponseSpecBuilder {

    public static ResponseSpecification build() {

        return new ResponseSpecBuilder().
                log(LogDetail.BODY).
                build();

    }

}
