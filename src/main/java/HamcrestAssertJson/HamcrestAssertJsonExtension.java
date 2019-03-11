package HamcrestAssertJson;

import static org.junit.Assert.assertThat;

import RestAssuredStore.ResponseType;
import RestAssuredStore.RestAssuredStoreInterface;

import static org.hamcrest.core.Is.is;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;


public class HamcrestAssertJsonExtension {

    private static RestAssuredStoreInterface store;

    public HamcrestAssertJsonExtension(RestAssuredStoreInterface store) {
        this.store = store;
    }

    public void sameJsonAs() {

        String testName = Thread.currentThread().getStackTrace()[2].getMethodName();

        if (store.responseExists(testName, ResponseType.EXPECTED)) {
            assertThat(
                    store.getResponseAsString(testName, ResponseType.ACTUAL),
                    is(sameJSONAs(
                            store.getResponseAsString(testName, ResponseType.EXPECTED)
                    ))
            );
        }

    }

}
