package wh.future.framework.web.request.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wh.future.framework.api.internal.logger.ApiErrorLogApi;
import wh.future.framework.common.enums.WebFilterOrderEnum;
import wh.future.framework.web.api_log.WebExceptionHandler;
import wh.future.framework.web.api_log.WebResponseBodyHandler;
import wh.future.framework.web.api_log.WebProperties;
import wh.future.framework.web.request.CacheRequestBodyFilter;
import wh.future.framework.web.util.WebFrameworkUtils;

import javax.annotation.Resource;
import javax.servlet.Filter;

/**
 * @author Administrator
 */
@AutoConfiguration
@EnableConfigurationProperties(WebProperties.class)
public class FutureWebAutoConfiguration implements WebMvcConfigurer {

    @Resource
    private WebProperties webProperties;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher antPathMatcher = new AntPathMatcher(".");
        configurer.addPathPrefix(webProperties.getWebApi().getPrefix(), clazz -> clazz.isAnnotationPresent(RestController.class)
                && antPathMatcher.match(webProperties.getWebApi().getPath(), clazz.getPackage().getName()));
    }

    /*初始化异常，将api注入进来方便异常后记录落库*/
    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public WebExceptionHandler globalExceptionHandler(ApiErrorLogApi apiErrorLogApi) {
        return new WebExceptionHandler(applicationName, apiErrorLogApi);
    }

    @Bean
    public WebResponseBodyHandler globalResponseBodyHandler() {
        return new WebResponseBodyHandler();
    }

    /*也是将配置数据初始化到util中去*/
    @Bean
    @SuppressWarnings("InstantiationOfUtilityClass")
    public WebFrameworkUtils webFrameworkUtils(WebProperties webProperties) {
        return new WebFrameworkUtils(webProperties);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterBean() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return doCreateFilterBean(new CorsFilter(source), WebFilterOrderEnum.CORS_FILTER);
    }

    @Bean
    public FilterRegistrationBean<CacheRequestBodyFilter> requestBodyCacheFilter() {
        return doCreateFilterBean(new CacheRequestBodyFilter(), WebFilterOrderEnum.REQUEST_BODY_CACHE_FILTER);
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    public static <T extends Filter> FilterRegistrationBean<T> doCreateFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }
}
