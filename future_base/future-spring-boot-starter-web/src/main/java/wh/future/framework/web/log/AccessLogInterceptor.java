package wh.future.framework.web.log;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import wh.future.framework.common.util.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import static cn.hutool.extra.spring.SpringUtil.getActiveProfile;

/**
 * @author Administrator
 */
@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {

    public static final String ATTRIBUTE_HANDLER_METHOD = "HANDLER_METHOD";

    private static final String ATTRIBUTE_STOP_WATCH = "REQUEST_INDICATORS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        HandlerMethod handlerMethod = handler instanceof HandlerMethod ? (HandlerMethod) handler : null;
        if (handlerMethod != null) {
            request.setAttribute(ATTRIBUTE_HANDLER_METHOD, handlerMethod);
        }
        if (!Objects.equals("prod",getActiveProfile())) {
            Map<String, String> queryString = ServletUtil.getParamsMap(request);
            String requestBody = ServletUtil.isJsonRequest(request) ? ServletUtil.getBody(request) : null;
            if (CollUtil.isEmpty(queryString) && StrUtil.isEmpty(requestBody)) {
                log.info("[preHandle][开始请求 URL({}) 无参数]", request.getRequestURI());
            } else {
                log.info("[preHandle][开始请求 URL({}) 参数({})]", request.getRequestURI(),
                        StrUtil.blankToDefault(requestBody, queryString.toString()));
            }
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            request.setAttribute(ATTRIBUTE_STOP_WATCH, stopWatch);
            printHandlerMethodPosition(handlerMethod);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!Objects.equals("prod",getActiveProfile())) {
            StopWatch stopWatch = (StopWatch) request.getAttribute(ATTRIBUTE_STOP_WATCH);
            stopWatch.stop();
            log.info("[afterCompletion][完成请求 URL({}) 耗时({} ms)]",
                    request.getRequestURI(), stopWatch.getTotalTimeMillis());
        }
    }

    private void printHandlerMethodPosition(HandlerMethod handlerMethod) {
        if (handlerMethod == null) {
            return;
        }
        Method method = handlerMethod.getMethod();
        Class<?> clazz = method.getDeclaringClass();
        try {
            List<String> clazzContents = FileUtil.readUtf8Lines(
                    ResourceUtil.getResource(null, clazz).getPath().replace("/target/classes/", "/src/main/java/")
                            + clazz.getSimpleName() + ".java");
            Optional<Integer> lineNumber = IntStream.range(0, clazzContents.size())
                    .filter(i -> clazzContents.get(i).contains(" " + method.getName() + "("))
                    .mapToObj(i -> i + 1)
                    .findFirst();
            if (!lineNumber.isPresent()) {
                return;
            }
            log.info("Controller 方法路径：{}({}.java:{})", clazz.getName(), clazz.getSimpleName(), lineNumber.get());
        } catch (Exception ignore) {
        }
    }

}
