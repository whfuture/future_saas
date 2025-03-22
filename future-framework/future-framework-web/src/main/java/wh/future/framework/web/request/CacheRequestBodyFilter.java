package wh.future.framework.web.request;

import org.springframework.web.filter.OncePerRequestFilter;
import wh.future.framework.common.util.ServletUtil;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


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

    private static class CacheRequestBodyWrapper extends HttpServletRequestWrapper {

        private final byte[] body;

        public CacheRequestBodyWrapper(HttpServletRequest request) {
            super(request);
            body = ServletUtil.getBodyBytes(request);
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(this.getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
            return new ServletInputStream() {

                @Override
                public int read() {
                    return inputStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener readListener) {
                }

                @Override
                public int available() {
                    return body.length;
                }

            };
        }
    }

}
