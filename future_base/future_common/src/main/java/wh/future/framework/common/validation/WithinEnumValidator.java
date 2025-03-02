package wh.future.framework.common.validation;

import wh.future.framework.common.enums.ArrayAble;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WithinEnumValidator implements ConstraintValidator<WithinEnum, Object> {

    private List<?> values;

    @Override
    public void initialize(WithinEnum constraintAnnotation) {
        ArrayAble<?>[] arrayAble = constraintAnnotation.values().getEnumConstants();
        if (arrayAble.length == 0) {
            this.values = Collections.emptyList();
        }else {
            this.values = Arrays.asList(arrayAble[0].arrayAble());
        }
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        if (o == null) {
            return true;
        }
        if(values.contains(o)){
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{values}",values.toString())).addConstraintViolation();
        return false;
    }
}
