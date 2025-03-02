package wh.future.framework.common.check;

import com.alibaba.fastjson.JSON;
import wh.future.framework.common.enums.ArrayAble;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;
import java.util.*;

public class InEnumsChecker implements ConstraintValidator<InEnums, Object> {

    private Set<?> enums;

    @Override
    public void initialize(InEnums annotation) {
        ArrayAble<?>[] arrayAble = annotation.value().getEnumConstants();
        if (arrayAble.length > 0) {
            this.enums = new HashSet<>(Arrays.asList(arrayAble[0].arrayAble()));
        } else {
            this.enums = Collections.emptySet();
        }
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object == null) {
            return true;
        }
        if (enums.contains(object)) {
            return true;
        }
        constraintValidatorContext.disableDefaultConstraintViolation();
        String defaultConstraintMessageTemplate = constraintValidatorContext.getDefaultConstraintMessageTemplate();
        constraintValidatorContext.
                buildConstraintViolationWithTemplate(MessageFormat.format(defaultConstraintMessageTemplate, JSON.toJSONString(enums))).
                addConstraintViolation();
        return false;
    }
}
