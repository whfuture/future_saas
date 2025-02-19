package wh.future.framework.common;


import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import wh.future.framework.common.enums.ErrorCodeConstants;
import wh.future.framework.common.exception.AssertUtils;

import java.text.MessageFormat;
import java.util.HashSet;
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

}
