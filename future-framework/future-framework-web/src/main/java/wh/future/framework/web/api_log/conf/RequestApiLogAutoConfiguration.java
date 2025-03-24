package wh.future.framework.web.api_log.conf;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wh.future.framework.rpc.api_log.ApiAccessLogApi;
import wh.future.framework.rpc.api_log.ApiErrorLogApi;
import wh.future.framework.common.enums.WebFilterOrderEnum;
import wh.future.framework.web.api_log.AccessLogInterceptor;
import wh.future.framework.web.api_log.ApiAccessLogFilter;
import wh.future.framework.web.api_log.WebProperties;
import wh.future.framework.web.request.conf.FutureWebAutoConfiguration;
import javax.servlet.Filter;


@AutoConfiguration(after = FutureWebAutoConfiguration.class)
@EnableFeignClients(clients = {ApiAccessLogApi.class, ApiErrorLogApi.class})
public class RequestApiLogAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnProperty(prefix = "future.api-access-log", value = "enable", matchIfMissing = true)
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogApi apiAccessLogApi) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogApi);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AccessLogInterceptor());
    }

}
