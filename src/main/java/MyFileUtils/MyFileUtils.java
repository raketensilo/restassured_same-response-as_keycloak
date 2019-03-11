package MyFileUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class MyFileUtils {

    private static final String RESOURCE_BASEPATH_DEFAULT = "./target/classes";
    private static final String TESTRESOURCE_BASEPATH_DEFAULT = "./target/test-classes";

    public static String getFileContentAsString(String path) {

        String fileContentAsString = null;
        try {
            fileContentAsString = FileUtils.readFileToString(
                    new File(path),
                    StandardCharsets.UTF_8.name()
            );
        } catch(IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return fileContentAsString;
    }

    public static String getFileContentAsString(String basePath, String relativePath) {

        String fileContentAsString = null;

        String path = createFilePath(basePath, relativePath);

        File f = new File(path);
        if (f.exists()) {
            fileContentAsString = getFileContentAsString(path);
        }

        return fileContentAsString;
    }

    public static Boolean fileExists(String basePath, String relativePath) {
        File f = new File(createFilePath(basePath, relativePath));
        if (f.exists())
            return true;
        else
            return false;
    }

    private static String createFilePath(String basePath, String relativePath) {
        if (basePath.endsWith("/")) {
            basePath = basePath.substring(0, basePath.length() - 1);
        }

        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }

        return basePath + "/" + relativePath;
    }

    /**
     * get Resource file content as string
     * @param resourcePath
     * @return
     */
    public static String getResourceAsString(String resourcePath) {

        String fileContentAsString = null;

        if (resourcePath != null) {
            try  {
                InputStream inputStream = MyFileUtils.class.getClassLoader().getResourceAsStream(resourcePath);
                fileContentAsString = IOUtils.toString(
                        inputStream,
                        StandardCharsets.UTF_8.name()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileContentAsString;
    }



    /**
     * get Resource file content as JSONArray
     * @param resourcePath
     * @return JSONArray
     */
    public static JSONArray getResourceAsJSONArray(String resourcePath) {

        JSONArray jsonArray = null;

        if (resourcePath != null) {
            try  {
                InputStream is = MyFileUtils.class.getResourceAsStream(resourcePath);
                jsonArray = new JSONArray(new JSONTokener(is));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    /**
     * get Resource file content as JSONObject
     * @param resourcePath
     * @return JSONObject
     */
    public static JSONObject getResourceAsJSONObject(String resourcePath) {

        JSONObject jsonObject = null;

        if (resourcePath != null) {
            try  {
                InputStream is = MyFileUtils.class.getResourceAsStream(resourcePath);
                jsonObject = new JSONObject(new JSONTokener(is));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    public static InputStream getResourceFromTargetAsStream(String basePath, String relativePath) {
        InputStream is = null;
        if (fileExists(basePath, relativePath)) {
            try {
                File f = new File(createFilePath(basePath, relativePath));
                is = new FileInputStream(f);
            } catch(IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        return is;
    }

    public static InputStream getResourceFromTargetAsStream(String relativePath) {
        return getResourceFromTargetAsStream(RESOURCE_BASEPATH_DEFAULT, relativePath);
    }

    public static InputStream getTestResourceFromTargetAsStream(String basePath, String relativePath) {
        return getResourceFromTargetAsStream(basePath, relativePath);
    }

    public static InputStream getTestResourceFromTargetAsStream(String relativePath) {
        return getResourceFromTargetAsStream(TESTRESOURCE_BASEPATH_DEFAULT, relativePath);
    }

    public static InputStream getResourceAsStream(String resourcePath) throws IOException {
        if (fileExists(RESOURCE_BASEPATH_DEFAULT, resourcePath))
            return getResourceFromTargetAsStream(RESOURCE_BASEPATH_DEFAULT, resourcePath);
        else
            return MyFileUtils.class.getClassLoader().getSystemClassLoader().getResourceAsStream(resourcePath);
    }

    public static InputStream getTestResourceAsStream(String testResourcePath) throws IOException {
        if (fileExists(TESTRESOURCE_BASEPATH_DEFAULT, testResourcePath))
            return getResourceFromTargetAsStream(TESTRESOURCE_BASEPATH_DEFAULT, testResourcePath);
        else
            return MyFileUtils.class.getClassLoader().getResourceAsStream(testResourcePath);
    }

}
