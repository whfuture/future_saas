package wh.future.framework.redis.event;


import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RedisEventTemplate {


    private final RedisTemplate<String,?> redisTemplate;

    private final List<RedisMessageInterceptor> interceptors= Collections.synchronizedList(new ArrayList<>());

    public RedisEventTemplate(RedisTemplate<String, ?> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T extends AbstractRedisMessage> void send(T message) {

    }

}
