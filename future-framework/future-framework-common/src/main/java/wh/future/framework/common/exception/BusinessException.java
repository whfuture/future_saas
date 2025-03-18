package wh.future.framework.common.exception;

import java.text.MessageFormat;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    private String message;

    public BusinessException() {
    }

    public BusinessException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public BusinessException(ErrorCode errorCode, Object... args) {
        this.code = errorCode.getCode();
        this.message = MessageFormat.format(errorCode.getMessage(), args);
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
