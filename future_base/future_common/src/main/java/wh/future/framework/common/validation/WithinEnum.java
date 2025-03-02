package wh.future.framework.common.validation;

import wh.future.framework.common.enums.ArrayAble;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * 判断在枚举范围内
 */
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {WithinEnumValidator.class, WithinEnumCollectionValidator.class}
)
public @interface WithinEnum {
    Class<? extends ArrayAble<?>> values();

    String message() default "must within the scope {values}";
}
