package wh.future.framework.common.util.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimestampToLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final TimestampToLocalDateTimeDeserializer INSTANCE = new TimestampToLocalDateTimeDeserializer();

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonParser.getValueAsLong()), ZoneId.systemDefault());
    }
}
