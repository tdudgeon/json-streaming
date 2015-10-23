package foo;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author timbo
 */
public class MyObjectJsonSerializer extends StdSerializer<MyObject> {
    
    public MyObjectJsonSerializer() {
        super(MyObject.class);
    }

    @Override
    public void serialize(MyObject t, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeStartObject();
        String text = t.getText();
        if (text != null) {
            jg.writeObjectField("text", text);
        }
        Integer number = t.getNumber();
        if (number != null) {
            jg.writeObjectField("number", number);
        }
        Stream<String> stream = t.getStream();
        if (stream != null) {
            jg.writeFieldName("stream");
            jg.writeStartArray();
            stream.forEachOrdered((s) -> {
                try {
                    jg.writeObject(s);
                } catch (IOException ex) {
                   throw new RuntimeException("Failed to write stream to JSON");
                }
            });
            
            jg.writeEndArray();
        }
        
        jg.writeEndObject();
    }
    
}
