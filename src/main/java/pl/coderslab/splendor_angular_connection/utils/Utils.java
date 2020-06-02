package pl.coderslab.splendor_angular_connection.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Utils {

    public String stringify(Map<String, String> map){
        ObjectMapper mapper = new ObjectMapper();
        String string = "";
        try {
            string = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return string;
    }
    public Map<String,String> jsonify(String string){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        TypeReference<HashMap<String, String>> typeRef
                = new TypeReference<HashMap<String, String>>() {};
        try {
            map = mapper.readValue(string, typeRef);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return map;
    }
}
