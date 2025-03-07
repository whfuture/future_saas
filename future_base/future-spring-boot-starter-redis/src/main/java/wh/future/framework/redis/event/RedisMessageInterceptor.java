package wh.future.framework.redis.event;

public interface RedisMessageInterceptor {

    default void sendMessageBefore(AbstractRedisMessage message){}

    default void sendMessageAfter(AbstractRedisMessage message){}

    default void consumeMessageBefore(AbstractRedisMessage message){}

    default void consumeMessageAfter(AbstractRedisMessage message){}

}
