package wh.future.framework.common.validation;

import cn.hutool.core.collection.CollectionUtil;
import wh.future.framework.common.enums.ArrayAble;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WithinEnumCollectionValidator implements ConstraintValidator<WithinEnum, Collection<?>> {

    private List<?> values;

    @Override
    public void initialize(WithinEnum constraintAnnotation) {
        ArrayAble<?>[] arrayAble = constraintAnnotation.values().getEnumConstants();
        if (arrayAble == null || arrayAble.length == 0) {
            this.values = Collections.emptyList();
        } else {
            this.values = Arrays.asList(arrayAble[0].arrayAble());
        }
    }

    @Override
    public boolean isValid(Collection<?> objects, ConstraintValidatorContext constraintValidatorContext) {
        if (objects == null || objects.isEmpty()) {
            return true;
        }
        if (values.containsAll(objects)) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{values}", CollectionUtil.join(objects, ","))).addConstraintViolation();
        return false;
    }
}
