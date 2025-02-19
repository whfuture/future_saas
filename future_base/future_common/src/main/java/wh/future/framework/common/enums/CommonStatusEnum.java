package wh.future.framework.common.enums;

import wh.future.framework.common.core.ArrayAble;
import java.util.Arrays;

/**
 *  enum
 */
public enum CommonStatusEnum implements ArrayAble<Integer> {

    Enable(1, "开启"),
    Disable(0, "关闭");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(CommonStatusEnum::getStatus).toArray(Integer[]::new);

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;


    CommonStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    @Override
    public Integer[] arrayAble() {
        return ARRAYS;
    }
}
