package wh.future.framework.security.operation;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;

@Aspect
@Component
public class OperationLogAspect {

    @Around("@annotation(OperationLog)")
    public Object logOperation(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 获取注解
        OperationLog operationLogAnnotation = method.getAnnotation(OperationLog.class);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            Class<?> argClass = arg.getClass();
            LogField logField = argClass.getAnnotation(LogField.class);
            if (logField != null) {
                String name = logField.name();
            }
        }
        // 执行目标方法
        Object result = joinPoint.proceed();
        // 记录操作信息
        return result;
    }

}
