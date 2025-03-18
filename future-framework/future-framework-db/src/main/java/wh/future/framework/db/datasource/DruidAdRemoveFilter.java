package wh.future.framework.db.datasource;

import com.alibaba.druid.util.Utils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Administrator
 */
public class DruidAdRemoveFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(request, response);
        // 重置缓冲区，响应头不会被重置
        response.resetBuffer();
        // 获取 common.js
        String text = Utils.readFromResource("support/http/resources/js/common.js");
        // 正则替换 banner, 除去底部的广告信息
        text = text.replaceAll("<a.*?banner\"></a><br/>", "");
        text = text.replaceAll("powered.*?shrek.wang</a>", "");
        response.getWriter().write(text);
    }

}
