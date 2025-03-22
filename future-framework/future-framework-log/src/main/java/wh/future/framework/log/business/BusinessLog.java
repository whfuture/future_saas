package wh.future.framework.log.business;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessLog {

    /*通过模块名称*/
    String moduleName() default "system";
    /*操作了什么*/
    String operationName() default "操作日志";
    /*操作人*/
    String operator();
    /*其他信息*/
    String extra() default "";

}
