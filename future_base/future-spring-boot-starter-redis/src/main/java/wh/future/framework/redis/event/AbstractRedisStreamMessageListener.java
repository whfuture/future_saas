package wh.future.framework.redis.event;

import cn.hutool.core.util.TypeUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import wh.future.framework.common.util.JsonUtils;
import java.lang.reflect.Type;
import java.util.List;


public abstract class AbstractRedisStreamMessageListener<T extends AbstractRedisStreamMessage>
        implements StreamListener<String, ObjectRecord<String, String>> {


    private final Class<T> messageType;

    @Getter
    private final String streamKey;

    @Value("${spring.application.name}")
    @Getter
    private String group;

    @Setter
    private RedisEventTemplate redisEventTemplate;

    @SneakyThrows
    protected AbstractRedisStreamMessageListener() {
        this.messageType = getMessageClass();
        this.streamKey = messageType.getDeclaredConstructor().newInstance().getStreamKey();
    }

    @Override
    public void onMessage(ObjectRecord<String, String> message) {
        // 消费消息
        T messageObj = JsonUtils.parseObject(message.getValue(), messageType);
        try {
            consumeMessageBefore(messageObj);
            this.onMessage(messageObj);
            redisEventTemplate.getRedisTemplate().opsForStream().acknowledge(group, message);
        } finally {
            consumeMessageAfter(messageObj);
        }
    }

    public abstract void onMessage(T message);

    @SuppressWarnings("unchecked")
    private Class<T> getMessageClass() {
        Type type = TypeUtil.getTypeArgument(getClass(), 0);
        if (type == null) {
            throw new IllegalStateException(String.format("类型(%s) 需要设置消息类型", getClass().getName()));
        }
        return (Class<T>) type;
    }

    private void consumeMessageBefore(AbstractRedisMessage message) {
        assert redisEventTemplate != null;
        List<RedisMessageInterceptor> interceptors = redisEventTemplate.getInterceptors();
        interceptors.forEach(interceptor -> interceptor.consumeMessageBefore(message));
    }

    private void consumeMessageAfter(AbstractRedisMessage message) {
        assert redisEventTemplate != null;
        List<RedisMessageInterceptor> interceptors = redisEventTemplate.getInterceptors();
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).consumeMessageAfter(message);
        }
    }

}
