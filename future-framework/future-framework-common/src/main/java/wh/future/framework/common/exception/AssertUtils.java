package wh.future.framework.common.exception;

import java.util.Objects;

public class AssertUtils {

    public static void isTrue(boolean b, ErrorCode errorCode) {
        if (!b) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isTrue(boolean b, ErrorCode errorCode, Object... args) {
        if (!b) {
            throw new BusinessException(errorCode,args);
        }
    }

    public static void isFalse(boolean b, ErrorCode errorCode) {
        if (b) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isFalse(boolean b, ErrorCode errorCode, Object... args) {
        if (b) {
            throw new BusinessException(errorCode,args);
        }
    }

    public static void nonNull(Object object, ErrorCode errorCode, Object... args) {
        if (Objects.isNull(object)) {
            throw new BusinessException(errorCode,args);
        }
    }

    public static void nonNull(Object object, ErrorCode errorCode) {
        if (Objects.isNull(object)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isNull(Object object, ErrorCode errorCode) {
        if (Objects.nonNull(object)) {
            throw new BusinessException(errorCode);
        }
    }

    public static void isNull(Object object, ErrorCode errorCode, Object... args) {
        if (Objects.nonNull(object)) {
            throw new BusinessException(errorCode,args);
        }
    }
}
