package wh.future.framework.security.operation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    /*通过模块名称*/
    String moduleName();
    /*操作了什么*/
    String operation();

}
