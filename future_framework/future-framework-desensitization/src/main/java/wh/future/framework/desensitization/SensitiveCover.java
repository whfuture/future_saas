package wh.future.framework.desensitization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Administrator
 */
@Slf4j
public class SensitiveCover extends JsonSerializer<String> implements ContextualSerializer {

    private boolean isDefault;

    private int start;

    private int end;

    private DefaultStrategy defaultStrategy;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        try {
            boolean flag = Boolean.FALSE;
            switch (defaultStrategy) {
                case DO_NOTHING:
                    if (isDefault) {
                        gen.writeString(DesensitizationUtil.replace(value, 0, value.length(), '*'));
                    } else {
                        gen.writeString(DesensitizationUtil.replace(value, start, end, '*'));
                    }
                    flag = Boolean.TRUE;
                    break;
            }
            if (!flag) {
                gen.writeString(defaultStrategy.getFunction().apply(value));
            }
        } catch (BeansException e) {
            gen.writeString(value);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Sensitive annotation = beanProperty.getAnnotation(Sensitive.class);
        if (annotation != null && Objects.equals(String.class, beanProperty.getType().getRawClass())) {
            isDefault = annotation.isDefault();
            start = annotation.start();
            end = annotation.end();
            defaultStrategy = annotation.defaultStrategy();
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }

}
