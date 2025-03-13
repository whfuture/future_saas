package wh.future;

import wh.future.framework.common.util.JsonUtil;
import wh.future.framework.desensitization.Sensitive;

public class Test {

    @Sensitive
    private String a;

    public static void main(String[] args) {
        Test test = new Test();
        test.a = "test128313h2hfjdsafs";
        System.out.println(JsonUtil.toJsonString(test));
    }
}
