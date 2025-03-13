package wh.future.framework.common.exception;


/**
 * 内部异常直接throw exception
 */
public class InternalException extends RuntimeException {

    private Integer code;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public InternalException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public InternalException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

}
