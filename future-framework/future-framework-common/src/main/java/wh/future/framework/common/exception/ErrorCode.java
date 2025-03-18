package wh.future.framework.common.exception;

public class ErrorCode {

    private final int code;
    private final String message;

    public ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
