package wh.future.framework.desensitization;

import java.util.function.Function;

/**
 * @author Administrator
 */

public enum DefaultStrategy {

    IDENTITY(s -> DesensitizationUtil.replace(s, 3, 4, '*')),

    PHONE(DesensitizationUtil::mobilePhone),

    ADDRESS(s -> DesensitizationUtil.address(s, 8)),

    EMAIL(DesensitizationUtil::email),

    BANK_CARD(DesensitizationUtil::bankCard),

    DO_NOTHING(DesensitizationUtil::doNothing);

    private final Function<String, String> function;

    DefaultStrategy(Function<String, String> function) {
        this.function = function;
    }

    public Function<String, String> getFunction() {
        return function;
    }
}
