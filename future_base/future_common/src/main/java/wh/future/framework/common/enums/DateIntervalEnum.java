package wh.future.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import wh.future.framework.common.core.ArrayAble;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum DateIntervalEnum implements ArrayAble<Integer> {

    DAY(1, "天"),
    WEEK(2, "周"),
    MONTH(3, "月"),
    QUARTER(4, "季度"),
    YEAR(5, "年")
    ;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(DateIntervalEnum::getInterval).toArray(Integer[]::new);

    /**
     * 类型
     */
    private final Integer interval;
    /**
     * 名称
     */
    private final String name;


    public static DateIntervalEnum valueOf(Integer interval) {
        return ArrayUtil.firstMatch(item -> item.getInterval().equals(interval), DateIntervalEnum.values());
    }

    @Override
    public Integer[] arrayAble() {
        return ARRAYS;    }
}