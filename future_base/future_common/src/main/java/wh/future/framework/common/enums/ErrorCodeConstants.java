package wh.future.framework.common.enums;

import wh.future.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode SUCCESS = new ErrorCode(200,"成功");
    ErrorCode ERROR = new ErrorCode(500,"系统内部错误");
}
