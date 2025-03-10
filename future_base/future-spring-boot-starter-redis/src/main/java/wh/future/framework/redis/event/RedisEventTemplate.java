package wh.future.framework.redis.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import wh.future.framework.common.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RedisEventTemplate {

    @Getter
    private final RedisTemplate<String, ?> redisTemplate;

    @Getter
    private final List<RedisMessageInterceptor> interceptors = new ArrayList<>();

    public <T extends AbstractRedisChannelMessage> void send(T message) {
        try {
            sendMessageBefore(message);
            // 发送消息
            redisTemplate.convertAndSend(message.getChannel(), JsonUtil.toJsonString(message));
        } finally {
            sendMessageAfter(message);
        }
    }

    public <T extends AbstractRedisStreamMessage> RecordId send(T message) {
        try {
            sendMessageBefore(message);
            // 发送消息
            return redisTemplate.opsForStream().add(StreamRecords.newRecord()
                    .ofObject(JsonUtil.toJsonString(message)) // 设置内容
                    .withStreamKey(message.getStreamKey())); // 设置 stream key
        } finally {
            sendMessageAfter(message);
        }
    }

    public void addInterceptor(RedisMessageInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    private void sendMessageBefore(AbstractRedisMessage message) {
        // 正序
        interceptors.forEach(interceptor -> interceptor.sendMessageBefore(message));
    }

    private void sendMessageAfter(AbstractRedisMessage message) {
        // 倒序
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).sendMessageAfter(message);
        }
    }

}
