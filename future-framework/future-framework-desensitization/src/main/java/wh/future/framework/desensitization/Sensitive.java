package wh.future.framework.desensitization;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static wh.future.framework.desensitization.DefaultStrategy.DO_NOTHING;

/**
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveCover.class)
public @interface Sensitive {

    DefaultStrategy defaultStrategy() default DO_NOTHING;

    boolean isDefault() default true;

    int start() default 0;

    int end() default Integer.MAX_VALUE;

}
