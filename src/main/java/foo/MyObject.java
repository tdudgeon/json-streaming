package foo;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.InputStream;
import java.util.stream.Stream;

/**
 *
 * @author timbo
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MyObject {

    private String text;
    private Integer number;
    private Stream<String> stream;

    public MyObject() {

    }

    public MyObject(String text, Integer number, Stream<String> stream) {
        this.text = text;
        this.number = number;
        this.stream = stream;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the value of number
     *
     * @return the value of number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * Set the value of number
     *
     * @param number new value of number
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    public Stream<String> getStream() {
        return stream;
    }

    public void setStream(Stream<String> stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "MyObject [" + text + ", " + number + ", " + stream + "]";
    }

}
