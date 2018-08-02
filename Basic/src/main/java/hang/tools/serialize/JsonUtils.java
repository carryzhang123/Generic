package hang.tools.serialize;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author ZhangHang
 * @create 2018-04-28 15:59
 **/
public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static TypeFactory TYPE_FACTORY = TypeFactory.defaultInstance();

    //对象转为json
    public static String object2String(Object object) {
        StringWriter writer = new StringWriter();
        try {
            MAPPER.writeValue(writer, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    //json转为对象
    public static <T> T string2Object(String content, Class<T> clz) throws IOException {
        try {
            JavaType type = TYPE_FACTORY.constructType(clz);
            return MAPPER.readValue(content, type);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
