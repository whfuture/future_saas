package wh.future.framework.redis.event;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractRedisChannelMessage extends AbstractRedisMessage {
    @JsonIgnore
    public String getChannel() {
        return getClass().getSimpleName();
    }
}
