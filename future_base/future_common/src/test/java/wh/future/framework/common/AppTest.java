package wh.future.framework.common;


import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import wh.future.framework.common.enums.DateIntervalEnum;
import wh.future.framework.common.enums.ErrorCodeConstants;
import wh.future.framework.common.exception.AssertUtils;
import wh.future.framework.common.util.date.TimeUtils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Unit test for simple App.
 */
public class AppTest {


    @Test
    public void test(){
        Set<String> enums=new HashSet<>();
        enums.add("A");
        enums.add("A");
        enums.add("A");
        enums.add("A");
        System.out.println(MessageFormat.format("woshi {0}", JSON.toJSONString(enums)));
        AssertUtils.nonNull(null, ErrorCodeConstants.Test,"n ggggg","ssss");
    }

    @Test
    public void test2(){
        String s = TimeUtils.formatDateRange(LocalDateTime.now(), DateIntervalEnum.MONTH);
        System.out.println(s);
        LocalDateTime year = TimeUtils.getFirstDayOfYear();
        System.out.println(year);
        System.out.println(TimeUtils.getFirstDayOfMonth());
        List<LocalDateTime[]> dateRangeList = TimeUtils.getDateRangeList(year, LocalDateTime.now(), DateIntervalEnum.MONTH);
        for (LocalDateTime[] localDateTimes : dateRangeList) {
            System.out.println(JSON.toJSONString(localDateTimes));
        }
    }

}
