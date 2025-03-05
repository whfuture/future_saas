package wh.future.framework.redis.event;

import com.fasterxml.jackson.annotation.JsonIgnore;


public abstract class AbstractRedisChannelMessage extends AbstractRedisMessage {

    @JsonIgnore // 避免序列化。原因是，Redis 发布 Channel 消息的时候，已经会指定。
    public String getChannel() {
        return getClass().getSimpleName();
    }

}
