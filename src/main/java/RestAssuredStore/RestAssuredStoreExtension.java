package RestAssuredStore;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.io.FileWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.ResponseSpecification;

import MyFileUtils.MyFileUtils;


/**
 * Using JUnit4 Testwatcher's 'starting' and 'finished' hook
 * to run code before and after each test
 */
public class RestAssuredStoreExtension extends TestWatcher implements RestAssuredStoreInterface {

    private String callerClassName;
    private String logPath;

    private LogConfig originalRestAssuredLogConfig;
    private PrintStream restAssuredLogPrintStream;
    private static ResponseSpecification logResponseBodySpec = LogResponseBodyResponseSpecBuilder.build();


    public RestAssuredStoreExtension setBasePath(String basePath) {
//        callerClassName = new Object(){}.getClass().getEnclosingClass().getSimpleName();

        String callerClassNameFromStackTrace = Thread.currentThread().getStackTrace()[3].getClassName();
        callerClassName = callerClassNameFromStackTrace.substring(callerClassNameFromStackTrace.lastIndexOf('.') + 1);

        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }

        logPath = basePath + "/" + callerClassName;

        return this;
    }

    public ResponseSpecification getLogSpec() {
        return logResponseBodySpec;
    }


    /**
     * runs before test
     * - backup original RestAssured LogConfig
     * - create new Printstream (i.e. a file on disk) for RestAssured logger to write to
     * - feed new Printstream into RestAssured global/default LogConfig
     */
    @Override
    public void starting(Description description) {
//        String callerMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        originalRestAssuredLogConfig = RestAssured.config().getLogConfig();

        File dir = new File(logPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(logPath + "/" + description.getMethodName() + ResponseType.ACTUAL.asSuffix());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        if (fileWriter != null) {
            restAssuredLogPrintStream = new PrintStream(new WriterOutputStream(fileWriter, StandardCharsets.UTF_8.name()), true);

            RestAssured.config = RestAssured.config().logConfig(
                    LogConfig.logConfig().defaultStream(restAssuredLogPrintStream).enablePrettyPrinting(true)
            );
        }

    }

    /**
     * runs after test
     * close restAssuredLogPrintStream after test
     * if actual response exists but expected does not, then copy actual into expected
     * if expected doesnt exist yet, copy expected from actual
     * restore original log config
     */
    @Override
    public void finished(Description description) {
//        String callerMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        if (restAssuredLogPrintStream != null) {
            restAssuredLogPrintStream.close();
        }

        File expected = new File(logPath + "/" + description.getMethodName() + ResponseType.EXPECTED.asSuffix());
        File actual = new File(logPath + "/" + description.getMethodName() + ResponseType.ACTUAL.asSuffix());

        if (actual.exists() && !expected.exists()) {
            try {
                FileUtils.copyFile(actual, expected);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        if (originalRestAssuredLogConfig != null) {
            RestAssured.config = RestAssuredConfig.config().logConfig(originalRestAssuredLogConfig);
        }

    }

    public String getResponseAsString(String testName, ResponseType responseType) {
//        String nameOfCallingMethod = Thread.currentThread().getStackTrace()[3].getMethodName();

        return MyFileUtils.getFileContentAsString(logPath, testName + responseType.asSuffix());
    }

    public Boolean responseExists(String testName, ResponseType responseType) {
//        String nameOfCallingMethod = Thread.currentThread().getStackTrace()[3].getMethodName();

        return MyFileUtils.fileExists(logPath, testName + responseType.asSuffix());
    }

}