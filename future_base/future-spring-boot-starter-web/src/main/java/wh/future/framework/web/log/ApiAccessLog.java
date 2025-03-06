package wh.future.framework.web.log;

public @interface ApiAccessLog {

    boolean enabled() default true;

    boolean requestEnable() default true;

    boolean responseEnable() default false;

    String[] sanitizeKeys() default {};

    String operateModule() default "";

    String operateName() default "";

    OperateTypeEnum[] operateType() default {};


}
