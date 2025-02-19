package wh.future.framework.common.pojo;

import lombok.Data;
import wh.future.framework.common.enums.ErrorCodeConstants;
import wh.future.framework.common.exception.ErrorCode;

import java.io.Serializable;
import java.text.MessageFormat;

@Data
public class R<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(ErrorCodeConstants.SUCCESS.getCode());
        r.setMsg(ErrorCodeConstants.SUCCESS.getMessage());
        r.setData(data);
        return r;
    }

    public static <T> R<T> error(Integer code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> error(Integer code, String msg, Object... args) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(MessageFormat.format(msg, args));
        return r;
    }

    public static <T> R<T> error(ErrorCode errorCode) {
        R<T> r = new R<>();
        r.setCode(errorCode.getCode());
        r.setMsg(errorCode.getMessage());
        return r;
    }

    public static <T> R<T> error(ErrorCode errorCode, Object... args) {
        R<T> r = new R<>();
        r.setCode(errorCode.getCode());
        r.setMsg(MessageFormat.format(errorCode.getMessage(), args));
        return r;
    }

}
