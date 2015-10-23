package foo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author timbo
 */
public class JsonStreamer {
    
    public static void main(String[] args) throws JsonProcessingException, IOException{
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(MyObject.class, new MyObjectJsonSerializer());
        module.addDeserializer(MyObject.class, new MyObjectJsonDeserializer());
        mapper.registerModule(module);
        
        List<String> strings = new ArrayList();
        for (int i=1; i<=10; i++) {
            strings.add("Hello " + i);
        }
        
        MyObject myo1 = new MyObject("Hello World!", 99, strings.stream());
        System.out.println("Object: " + myo1);
        String json = mapper.writeValueAsString(myo1);
        System.out.println("JSON: " + json);
       
        MyObject myo2 = mapper.readValue(json, MyObject.class); 
        System.out.println("Object: " + myo2);
    }
    
}
