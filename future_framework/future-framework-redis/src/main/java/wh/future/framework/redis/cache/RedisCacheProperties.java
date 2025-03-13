package wh.future.framework.redis.cache;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties("future.redis-cache")
public class RedisCacheProperties {

    private static final Integer REDIS_DEFAULT_SCAN_SIZE = 30;

    private Integer scanSize = REDIS_DEFAULT_SCAN_SIZE;

}
