package wh.future.framework.web.request;

import org.springframework.web.filter.OncePerRequestFilter;
import wh.future.framework.common.util.ServletUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Request Body 缓存 Filter，实现它的可重复读取
 */
public class CacheRequestBodyFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(new CacheRequestBodyWrapper(request), response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // 只处理 json 请求内容
        return !ServletUtil.isJsonRequest(request);
    }

}
