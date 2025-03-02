package wh.future.framework.redis.cache.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@AutoConfiguration
@EnableCaching
@EnableConfigurationProperties({RedisCacheProperties.class, CacheProperties.class})
public class RedisCacheConfiguration {

}
