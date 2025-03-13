package wh.future.framework.web.api_log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAccessLog {

    boolean enabled() default true;

    boolean requestEnable() default true;

    boolean responseEnable() default false;

    String[] sanitizeKeys() default {};

    String operateModule() default "";

    String operateName() default "";

    OperateTypeEnum[] operateType() default {};


}
