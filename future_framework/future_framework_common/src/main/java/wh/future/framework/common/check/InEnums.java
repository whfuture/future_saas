package wh.future.framework.common.check;


import wh.future.framework.common.enums.ArrayAble;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {InEnumsChecker.class}
)
public @interface InEnums {

    Class<? extends ArrayAble<?>> value();

    String message() default "必须在指定范围:{}";



}
