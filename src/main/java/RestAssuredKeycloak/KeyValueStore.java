package RestAssuredKeycloak;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class KeyValueStore {

    private Map<Object, Object> keyValueMap;

    public void set(String keyValueJson) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            Map<Object, Object> map = mapper.readValue(keyValueJson, new TypeReference<Map<String, String>>(){});

            keyValueMap = map;

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Object getValue(Object key) {
        return keyValueMap.get(key);
    }

}
