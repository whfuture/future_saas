package wh.future.framework.web.api_log;


import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import wh.future.framework.common.pojo.R;
import wh.future.framework.web.util.WebFrameworkUtils;


@ControllerAdvice
public class WebResponseBodyHandler implements ResponseBodyAdvice {

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (returnType.getMethod() == null) {
            return false;
        }
        return returnType.getMethod().getReturnType() == R.class;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        WebFrameworkUtils.setR(((ServletServerHttpRequest) request).getServletRequest(), (R<?>) body);
        return body;
    }

}
