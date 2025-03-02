package wh.future.framework.redis.cache.config;


import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Validated
@ConfigurationProperties("future.redis-cache")
public class RedisCacheProperties {

    private static final Integer REDIS_DEFAULT_SCAN_SIZE = 30;

    private Integer scanSize = REDIS_DEFAULT_SCAN_SIZE;

}
