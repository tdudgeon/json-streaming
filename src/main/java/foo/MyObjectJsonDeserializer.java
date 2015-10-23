package foo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author timbo
 */
public class MyObjectJsonDeserializer extends StdDeserializer<MyObject> {

    public MyObjectJsonDeserializer() {
        super(MyObject.class);
    }

    @Override
    public MyObject deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {

        MyObject myo = new MyObject();
        JsonToken currentToken = jp.getCurrentToken();
        //System.out.println("CT: " + currentToken);
        while (null != (currentToken = jp.nextToken())) {
            //System.out.println("CT: " + currentToken);
            switch (currentToken) {
                case FIELD_NAME:
                    String name = jp.getCurrentName();
                    currentToken = jp.nextToken();
                    //System.out.println("NM: " + name);
                    if ("text".equals(name)) {
                        String t = jp.getValueAsString();
                        //System.out.println("TX: " + t);
                        myo.setText(t);
                    } else if ("number".equals(name)) {
                        int i = jp.getValueAsInt();
                        //System.out.println("IN: " + i);
                        myo.setNumber(i);
                    } else if ("stream".equals(name)) {
                        currentToken = jp.nextToken();
                        //System.out.println("CT: " + currentToken);
                        Iterator<String> it = jp.readValuesAs(String.class);
                        /* the problem here is that all we can do is materialize the Iterator 
                        as we need to to be completely consumed before we can read the rest of the JSON.
                        We cannot consume the Iterator later.
                        
                        The only solution I can think of is to use a SAX-style event processing model
                        where a Stream wrapping the Iterator is passed as an event, and when the Stream
                        is closed the Iterator is read to completion (in case all elements were not consumed) 
                        and then the remaining JSON tokens processed.
                        */
                        List<String> strings = new ArrayList<>();
                        while (it.hasNext()) {
                            String s = it.next();
                            strings.add(s);
                            //System.out.println("ST: " + s);
                        }
                        myo.setStream(strings.stream());
                        //System.out.println("CT: " + jp.getCurrentToken());
                    }
                    //System.out.println("CT: " + currentToken);
            }
        }

        return myo;
    }

}
