package wh.future.framework.common.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;

import java.io.IOException;

@JacksonStdImpl
public class NumberSerializer extends com.fasterxml.jackson.databind.ser.std.NumberSerializer {
    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;

    public static final NumberSerializer INSTANCE = new NumberSerializer(Number.class);

    public NumberSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator g, SerializerProvider provider) throws IOException {
        if(value.longValue()>MIN_SAFE_INTEGER && value.longValue()<MAX_SAFE_INTEGER){
            super.serialize(value, g, provider);
        }
        g.writeString(value.toString());
    }
}
